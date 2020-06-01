male(bill).
female(mary).

female(shelley).
male(john).
female(lisa).

male(fred).
male(sam).

male(tom).
male(alex).
male(drake).
male(elon).
female(grims).
female(emilly).
female(tamy).
female(fiona).

male(xae-12).

parent(bill, shelley).
parent(mary, shelley).
parent(bill, john).
parent(mary, john).
parent(bill, lisa).
parent(mary, lisa).

parent(shelley, sam).
parent(fred, sam).

parent(elon, xae-12).
parent(groms, xae-12).
parent(fiona, elon).
parent(drake, elon).
parent(emilly, alex).
parent(tom, alex).

/* Rules */

father(X, Y) :- 
    parent(X, Y),
    male(X).

mother(X, Y) :- 
    parent(X, Y), 
    female(X).

child(X, Y) :- 
    parent(Y, X).

daughter(X, Y) :- 
    child(X, Y), 
    female(X).
        
son(X, Y) :- 
    child(X, Y), 
    male(X).

ancestor(X, Y) :- 
    parent(X, Y).

ancestor(X, Y) :- 
    parent(Z, Y), 
    ancestor(X, Z).

descendent(X, Y) :- 
    ancestor(Y, X).

sibling(X, Y) :- 
    parent(Z, X), 
    parent(Z, Y).

brother(X, Y) :- 
    sibling(X, Y), 
    male(X).

sister(X, Y) :-
    sibling(X, Y), 
    female(X).

cousin(X,Y):-
	parent(Z,X),
	sibling(Z,W),
	parent(W,Y).

uncle(X,Y):-
	parent(Z,Y),
	sibling(Z,X),
	male(X).

uncle(X,Y):-
	partner(X,Z),
	parent(W,Y),
	sibling(W,Z),
	female(Z).		

aunt(X,Y):-
	parent(Z,Y),
	sibling(Z,X),
	female(X).
	
aunt(X,Y):-
	partner(X,Z),
	parent(W,Y),
	sibling(W,Z),
	male(Z).