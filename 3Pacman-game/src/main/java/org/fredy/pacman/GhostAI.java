// The MIT License (MIT)
// 
// Copyright (c) 2014 Fredy Wijaya
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy of
// this software and associated documentation files (the "Software"), to deal in
// the Software without restriction, including without limitation the rights to
// use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
// the Software, and to permit persons to whom the Software is furnished to do so,
// subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
// FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
// COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
// IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
// CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

package org.fredy.pacman;

import javafx.util.Pair;

import java.util.*;


public class GhostAI {
    private GhostAI() {
        // Prevent instantiation.
    }

    public static Player.Position getGhostNextPosition(Player pacMan, Player ghost, NodeType[][] maze) {
        Node startNode = new Node();
        Node targetNode = new Node();
        startNode.setColumn(ghost.getCurrentColumn());
        startNode.setRow(ghost.getCurrentRow());

        targetNode.setColumn(pacMan.getCurrentColumn());
        targetNode.setRow(pacMan.getCurrentRow());

        Player.Position position = null;


        /**
         *
         *
         * Тут змінюється алгоритм пошуку
         * A* / BFS / DFS
         *
         */
//        position = A_star(startNode, targetNode, maze);
//        position = BFS(ghost, pacMan, CreateGraph(maze));
        position = DFS(ghost, pacMan, CreateGraph(maze), ghost.getPathToTarget());

        return position;
    }


    private static Player.Position getNextPosition(Node startNode, Node nextNode) {
        Player.Position position = null;
        if (nextNode != null) {
            if (startNode.getColumn() > nextNode.getColumn()) {
                position = Player.Position.LEFT;
            } else if (startNode.getColumn() < nextNode.getColumn()) {
                position = Player.Position.RIGHT;
            } else if (startNode.getRow() > nextNode.getRow()) {
                position = Player.Position.UP;
            } else if (startNode.getRow() < nextNode.getRow()) {
                position = Player.Position.DOWN;
            }
        }
        return position;
    }

    private static Node getNextMove(Node startNode, Node targetNode,
                                    List<Node> closeNodeList) {
        int column = targetNode.getColumn();
        int row = targetNode.getRow();
        Node node = null;
        while ((node = findNode(column, row, closeNodeList)) != null) {
            if (node.getParentColumn() == startNode.getColumn()
                    && node.getParentRow() == startNode.getRow()) {
                break;
            } else {
                column = node.getParentColumn();
                row = node.getParentRow();
            }
        }
        return node;
    }

    private static Node findNode(int column, int row, List<Node> closeNodeList) {
        for (Node node : closeNodeList) {
            if (node.getColumn() == column && node.getRow() == row) {
                return node;
            }
        }
        return null;
    }

    private static List<Node> getAdjacentNodes(int column, int row, NodeType[][] maze) {
        List<Node> nodeList = new ArrayList<Node>();
        if (!isBlockedTerrain(column + 1, row, maze)) {
            Node node = new Node();
            node.setColumn(column + 1);
            node.setRow(row);
            nodeList.add(node);
        }

        if (!isBlockedTerrain(column - 1, row, maze)) {
            Node node = new Node();
            node.setColumn(column - 1);
            node.setRow(row);
            nodeList.add(node);
        }

        if (!isBlockedTerrain(column, row + 1, maze)) {
            Node node = new Node();
            node.setColumn(column);
            node.setRow(row + 1);
            nodeList.add(node);
        }

        if (!isBlockedTerrain(column, row - 1, maze)) {
            Node node = new Node();
            node.setColumn(column);
            node.setRow(row - 1);
            nodeList.add(node);
        }
        return nodeList;
    }

    private static boolean isBlockedTerrain(int column, int row, NodeType[][] maze) {
        boolean blocked = false;
        try {
            if (maze[row][column] == NodeType.WALL) {
                blocked = true;
            }
        } catch (Exception ex) {
            // Do nothing.
        }
        return blocked;
    }

    private static int calculateDistance(int srcColumn, int srcRow, int destColumn,
                                         int destRow) {
        return (Math.abs(srcColumn - destColumn) * 10)
                + (Math.abs(srcRow - destRow) * 10);
    }

    private static Node getLowestDistanceNode(List<Node> openNodeList) {
        Node lowestDistanceNode = null;
        if (openNodeList.size() > 0) {
            lowestDistanceNode = openNodeList.get(0);
            for (int i = 1; i < openNodeList.size(); i++) {
                Node n = openNodeList.get(i);
                if (n.getDistance() < lowestDistanceNode.getDistance()) {
                    lowestDistanceNode = n;
                }
            }
        }
        return lowestDistanceNode;
    }


    /* ---------------------------------------- */

    public static class GraphNode {
        int col, row;
        List<GraphNode> neighbours;

        GraphNode(int r, int c) {
            row = r;
            col = c;
            neighbours = new ArrayList<>();
        }
    }

    public static Player.Position whereToMove(Player ghost, GraphNode nextStep) {
        if (ghost.getCurrentRow() - nextStep.row < 0) {
            return Player.Position.DOWN;
        } else if (ghost.getCurrentRow() - nextStep.row > 0) {
            return Player.Position.UP;
        } else if (ghost.getCurrentColumn() - nextStep.col < 0) {
            return Player.Position.RIGHT;
        } else {
            return Player.Position.LEFT;
        }
    }

    public static GraphNode getPrevStep(Player ghost, Player.Position lastPostion){
        if (lastPostion == Player.Position.DOWN){
            return new GraphNode(ghost.getCurrentRow()-1, ghost.getCurrentColumn());
        } else if (lastPostion == Player.Position.UP){
            return new GraphNode(ghost.getCurrentRow()+1, ghost.getCurrentColumn());
        } else if (lastPostion == Player.Position.LEFT){
            return new GraphNode(ghost.getCurrentRow(), ghost.getCurrentColumn()+1);
        } else {
            return new GraphNode(ghost.getCurrentRow(), ghost.getCurrentColumn()-1);
        }
    }


    public static Map<Pair<Integer, Integer>, GraphNode> CreateGraph(NodeType[][] maze) {
        Map<Pair<Integer, Integer>, GraphNode> NodesFromMaze = new HashMap<>();

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] != NodeType.WALL) {
                    NodesFromMaze.put(new Pair(i, j), new GraphNode(i, j));
                }
            }
        }

        for (Map.Entry<Pair<Integer, Integer>, GraphNode> entry : NodesFromMaze.entrySet()) {
            int row = entry.getKey().getKey();
            int col = entry.getKey().getValue();

            int maxRow = maze.length - 1;
            int maxCol = maze[0].length - 1;

            if (col < maxCol && NodesFromMaze.containsKey(new Pair<>(row, col + 1))) {
                entry.getValue().neighbours.add(NodesFromMaze.get(new Pair<>(row, col + 1)));
            }
            if (row > 0 && NodesFromMaze.containsKey(new Pair<>(row - 1, col))) {
                entry.getValue().neighbours.add(NodesFromMaze.get(new Pair<>(row - 1, col)));
            }
            if (col > 0 && NodesFromMaze.containsKey(new Pair<>(row, col - 1))) {
                entry.getValue().neighbours.add(NodesFromMaze.get(new Pair<>(row, col - 1)));
            }
            if (row < maxRow && NodesFromMaze.containsKey(new Pair<>(row + 1, col))) {
                entry.getValue().neighbours.add(NodesFromMaze.get(new Pair<>(row + 1, col)));
            }


        }

        return NodesFromMaze;
    }

    private static Player.Position A_star(Node startNode, Node targetNode, NodeType[][] maze) {
        List<Node> openNodeList = new ArrayList<Node>();
        List<Node> closeNodeList = new ArrayList<Node>();
        openNodeList.add(startNode);
        while (openNodeList.size() > 0 && !closeNodeList.contains(targetNode)) {
            Node currentNode = getLowestDistanceNode(openNodeList);
            closeNodeList.add(currentNode);
            openNodeList.remove(currentNode);

            List<Node> adjacentNodes = getAdjacentNodes(currentNode.getColumn(),
                    currentNode.getRow(), maze);
            for (Node adjacentNode : adjacentNodes) {
                if (!closeNodeList.contains(adjacentNode)) {
                    adjacentNode.setParentColumn(currentNode.getColumn());
                    adjacentNode.setParentRow(currentNode.getRow());
                    if (!openNodeList.contains(adjacentNode)) // Open list
                    {
                        adjacentNode.setDistance(calculateDistance(
                                adjacentNode.getColumn(), adjacentNode.getRow(),
                                targetNode.getColumn(), targetNode.getRow()));
                        openNodeList.add(adjacentNode);
                    }
                }
            }
        }
        return getNextPosition(startNode,
                getNextMove(startNode, targetNode, closeNodeList));
    }

    public static Player.Position BFS(Player ghost, Player target, Map<Pair<Integer, Integer>, GraphNode> graph) {
        Map<GraphNode, GraphNode> backtrack = new HashMap<>();
        Set<GraphNode> visited = new HashSet<>();

        Pair<Integer, Integer> ghostPosition = new Pair<>(ghost.getCurrentRow(), ghost.getCurrentColumn());

        Queue<GraphNode> bfs_que = new ArrayDeque<>();
        bfs_que.add(graph.get(ghostPosition));
        backtrack.put(graph.get(ghostPosition), null);

        while (!bfs_que.isEmpty()) {
            GraphNode top = bfs_que.poll();
            visited.add(top);

            if (top.row == target.getCurrentRow() && top.col == target.getCurrentColumn()) {
                break;
            }

            List<GraphNode> possibleStep = new ArrayList<>();
            for (GraphNode next : top.neighbours) {
                if (!visited.contains(next) ){
                    backtrack.put(next, top);
                    bfs_que.add(next);
                }
            }
        }

        List<GraphNode> path = getPathFromBackTrack(graph.get(new Pair<>(target.getCurrentRow(), target.getCurrentColumn())), backtrack);
        GraphNode nextStep = path.get(path.size() - 1);

        return whereToMove(ghost, nextStep);

    }

    public static List<GraphNode> getPathFromBackTrack(GraphNode target, Map<GraphNode, GraphNode> backtrack) {
        List<GraphNode> path = new ArrayList<>();

        GraphNode current = target;
        GraphNode parent = backtrack.get(target);


        while (parent != null) {
            path.add(current);
            current = parent;
            parent = backtrack.get(current);
        }

        return path;
    }


    public static Player.Position DFS(Player ghost, Player target, Map<Pair<Integer, Integer>, GraphNode> graph, List<GraphNode> pathToTarget) {
        if(pathToTarget.size() > 0){
            GraphNode nextStep = pathToTarget.get(pathToTarget.size()-1);
            pathToTarget.remove(nextStep);
            return whereToMove(ghost, nextStep);
        }

        Stack<GraphNode> dfs_stack = new Stack<>();
        Set<GraphNode> visited = new HashSet<>();

        Map<GraphNode, GraphNode> backtrack = new HashMap<>();

        Pair<Integer, Integer> coords = new Pair<>(ghost.getCurrentRow(), ghost.getCurrentColumn());
        dfs_stack.push(graph.get(coords));
        backtrack.put(graph.get(coords), null);
        visited.add(graph.get(coords));

        while (!dfs_stack.empty()) {
            GraphNode current = dfs_stack.peek();

            if (current.row == target.getCurrentRow() && current.col == target.getCurrentColumn()) {
                break;
            }
            dfs_stack.pop();
            for (GraphNode next : current.neighbours) {
                GraphNode prevGraph = getPrevStep(ghost, ghost.getPosition());
                if (!visited.contains(next) && (!(next.row == prevGraph.row) || !(next.col == prevGraph.col))) {
//                    possibleStep.add(next);
                    visited.add(next);
                    dfs_stack.push(next);
                    backtrack.put(next,current);
                }
            }

        }

        List<GraphNode> path = getPathFromBackTrack(graph.get(new Pair<>(target.getCurrentRow(), target.getCurrentColumn())), backtrack);
        GraphNode nextStep = path.get(path.size() - 1);
        path.remove(nextStep);
        ghost.setPathToTarget(path);

//        for (int i = 0; i < maze.length; i++) {
//            for (int j = 0; j < maze[i].length; j++) {
//                if(maze[i][j] == NodeType.DOT){
//                    maze[i][j] = NodeType.BLANK;
//                }
//            }
//        }
//        for(GraphNode node : path) {
//            if (maze[node.row][node.col] != NodeType.PACMAN) {
//                maze[node.row][node.col] = NodeType.DOT;
//            }
//        }

        return whereToMove(ghost, nextStep);
    }
}
