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

import java.util.ArrayList;
import java.util.List;

public class PacManGame {
    private static final int SCORE = 10;
    private Player pacMan;
    private List<Player> ghosts = new ArrayList<Player>();
    private MovementEngine movementEngine;
    private int nDots;
    private Game game;
    private int totalScore;
    private static NodeType[][] maze = {
            // 0
            {NodeType.GHOST, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.WALL, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK},
            // 1
            {NodeType.BLANK, NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL, NodeType.BLANK,
                    NodeType.WALL, NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.WALL, NodeType.BLANK},
            // 2
            {NodeType.BLANK, NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL, NodeType.BLANK,
                    NodeType.WALL, NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.WALL, NodeType.BLANK},
            // 3
            {NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK},
            // 4
            {NodeType.BLANK, NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.BLANK, NodeType.WALL, NodeType.WALL, NodeType.WALL, NodeType.WALL,
                    NodeType.WALL, NodeType.BLANK, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.WALL, NodeType.BLANK},
            // 5
            {NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.WALL,
                    NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.WALL, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK, NodeType.WALL, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK},
            // 6
            {NodeType.BLANK, NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL, NodeType.BLANK,
                    NodeType.WALL, NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.WALL, NodeType.BLANK},
            // 7
            {NodeType.BLANK, NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK,
                    NodeType.WALL, NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.WALL, NodeType.BLANK},
            // 8
            {NodeType.BLANK, NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.WALL, NodeType.BLANK},
            // 9
            {NodeType.BLANK, NodeType.BLANK, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.BLANK, NodeType.WALL, NodeType.WALL, NodeType.WALL, NodeType.WALL,
                    NodeType.WALL, NodeType.BLANK, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.BLANK, NodeType.BLANK},
            // 10
            {NodeType.WALL, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.WALL, NodeType.WALL, NodeType.WALL, NodeType.WALL,
                    NodeType.WALL, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.WALL},
            // 11
            {NodeType.BLANK, NodeType.BLANK, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.BLANK, NodeType.WALL, NodeType.WALL, NodeType.WALL, NodeType.WALL,
                    NodeType.WALL, NodeType.BLANK, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.BLANK, NodeType.BLANK},
            // 12
            {NodeType.BLANK, NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.WALL, NodeType.BLANK},
            // 13
            {NodeType.BLANK, NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.BLANK, NodeType.WALL, NodeType.WALL, NodeType.WALL, NodeType.WALL,
                    NodeType.WALL, NodeType.BLANK, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.WALL, NodeType.BLANK},
            // 14
            {NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.WALL, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK,},
            // 15
            {NodeType.BLANK, NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL, NodeType.BLANK,
                    NodeType.WALL, NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.WALL, NodeType.BLANK},
            // 16
            {NodeType.BLANK, NodeType.BLANK, NodeType.WALL, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.WALL, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.WALL,
                    NodeType.BLANK, NodeType.BLANK},
            // 17
            {NodeType.BLANK, NodeType.BLANK, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.BLANK, NodeType.BLANK},
            // 18
            {NodeType.WALL, NodeType.BLANK, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.BLANK, NodeType.WALL, NodeType.WALL, NodeType.WALL, NodeType.WALL,
                    NodeType.WALL, NodeType.BLANK, NodeType.WALL, NodeType.BLANK, NodeType.WALL,
                    NodeType.BLANK, NodeType.WALL},
            // 19
            {NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.WALL,
                    NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.WALL, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK, NodeType.WALL, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK,},
            // 20
            {NodeType.BLANK, NodeType.WALL, NodeType.WALL, NodeType.WALL, NodeType.WALL,
                    NodeType.WALL, NodeType.WALL, NodeType.BLANK, NodeType.WALL, NodeType.BLANK,
                    NodeType.WALL, NodeType.WALL, NodeType.WALL, NodeType.WALL, NodeType.WALL,
                    NodeType.WALL, NodeType.BLANK},
            // 22
            {NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK, NodeType.BLANK,
                    NodeType.BLANK, NodeType.BLANK}};

    public void randomSpawnNodeType(NodeType[][] maze, NodeType toSpawn) {
        int row, col;
        do {
            row = (int) (Math.random() * maze.length);
            col = (int) (Math.random() * maze[0].length);
        } while (maze[row][col] != NodeType.BLANK);

        maze[row][col] = toSpawn;
    }

    public Pair<Integer, Integer> findPacMan() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == NodeType.PACMAN) {
                    return new Pair<>(i, j);
                }
            }
        }

        return new Pair<>(0, maze.length - 1);
    }


    public PacManGame(Game game) {
        randomSpawnNodeType(maze, NodeType.PACMAN);
        this.game = game;
        pacMan = new Player(Player.PlayerType.PACMAN);
        pacMan.setCurrentColumn(findPacMan().getValue());
        pacMan.setCurrentRow(findPacMan().getKey());
        pacMan.setPosition(Player.Position.RIGHT);
        pacMan.setNumDotsEaten(0);

        movementEngine = new MovementEngine(pacMan, maze);

        start();
    }

    public void start() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == NodeType.DOT) {
                    nDots++;
                } else if (maze[i][j] == NodeType.GHOST) {
                    Player ghost = new Player(Player.PlayerType.GHOST);
                    ghost.setCurrentRow(i);
                    ghost.setCurrentColumn(j);
                    ghost.setPosition(Player.Position.RIGHT);
                    ghosts.add(ghost);
                }
            }
        }

    }

    public void movePacMan() {
        move(pacMan);
        totalScore = pacMan.getNumDotsEaten() * SCORE;
        if (pacMan.getNumDotsEaten() == nDots + 1) {
            game.win();
        } else {
            if (pacMan.isDead()) {
                game.gameOver();
            }
        }
    }

    public void moveGhost() {
        for (Player ghost : ghosts) {
            move(ghost);
        }
        if (pacMan.isDead()) {
            game.gameOver();
        }
    }

    private void move(Player player) {
        if (player.getPosition() == Player.Position.LEFT) {
            movementEngine.left(player);
        } else if (player.getPosition() == Player.Position.RIGHT) {
            movementEngine.right(player);
        } else if (player.getPosition() == Player.Position.UP) {
            movementEngine.up(player);
        } else if (player.getPosition() == Player.Position.DOWN) {
            movementEngine.down(player);
        }
    }

    public Player getPacMan() {
        return pacMan;
    }

    public NodeType[][] getMaze() {
        return maze;
    }

    public List<Player> getGhosts() {
        return ghosts;
    }

    public int getTotalScore() {
        return totalScore;
    }
}
