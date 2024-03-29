package Objects;

import Images.Load;
import java.awt.Graphics;

/**
 *
 * @author Katze
 */
public class Enemy implements GameObject
{

    public Battleship battleship;
    public Cruise cruise;
    public Destroyer destroyer1;
    public Destroyer destroyer2;
    public Submarine submarine;
    private States ships;
    public char[][] board =
    {
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'}
    };
    public char[][] boardEnemy =
    {
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'}
    };
    public int[][] positionShips = new int[5][3];

    public Enemy(States ships)
    {
        this.ships = ships;
        setShipsEnemys();
        setImages();
    }

    public void shot()
    {
        int x;
        int y;
        boolean exist = true;
        while (exist)
        {
            boolean hit = false;
            x = (int) (Math.random() * 10);
            y = (int) (Math.random() * 10);
            if (boardEnemy[y][x] == '*')
            {
                boardEnemy[y][x] = 'x';
                int posBor = getBoardPost(x, y);
                int X = ships.board.boxBoard[posBor][0];
                int Y = ships.board.boxBoard[posBor][2];
                if (ships.battleship.getX1() <= X && ships.battleship.getX2() > X && ships.battleship.getY1() <= Y && ships.battleship.getY2() > Y)
                {
                    ships.battleship.reduceLife();
                    hit = true;
                }
                if (ships.cruise.getX1() <= X && ships.cruise.getX2() > X && ships.cruise.getY1() <= Y && ships.cruise.getY2() > Y)
                {
                    ships.cruise.reduceLife();
                    hit = true;
                }
                if (ships.destroyer1.getX1() <= X && ships.destroyer1.getX2() > X && ships.destroyer1.getY1() <= Y && ships.destroyer1.getY2() > Y)
                {
                    ships.destroyer1.reduceLife();
                    hit = true;
                }
                if (ships.destroyer2.getX1() <= X && ships.destroyer2.getX2() > X && ships.destroyer2.getY1() <= Y && ships.destroyer2.getY2() > Y)
                {
                    ships.destroyer2.reduceLife();
                    hit = true;
                }
                if (ships.submarine.getX1() <= X && ships.submarine.getX2() > X && ships.submarine.getY1() <= Y && ships.submarine.getY2() > Y)
                {
                    ships.submarine.reduceLife();
                    hit = true;
                }
                if (hit)
                    ships.creteHit(true, 0, X, Y);
                else
                    ships.creteHit(true, 1, X, Y);
                exist = false;
            }
        }
        ships.turn = true;
    }

    private int getBoardPost(int x, int y)
    {
        int contx = 0;
        int conty = 0;
        for (int i = 0; i < 100; i++)
            if (contx == x && conty == y)
                return i;
            else if (conty < 9)
                conty++;
            else
            {
                contx++;
                conty = 0;
            }
        return -1;
    }

    private void setShipsEnemys()
    {
        setShips();
        boolean valid = true;
        int numComp = 0;
        while (valid)
        {
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++)
                    if (board[i][j] == '+')
                        numComp++;
            if (numComp == 17)
                valid = false;
            else
            {
                for (int i = 0; i < 10; i++)
                    for (int j = 0; j < 10; j++)
                        board[i][j] = '*';
                numComp = 0;
                setShips();
            }
        }
    }

    private void ramdomPos()
    {
        int cont = 0;
        boolean comp = true;
        for (int i = 5; i > 1; i--)
        {
            int direc = (int) (Math.random() * 2);
            int num = (int) (Math.random() * 10);
            if (num >= (9 - i))
                num = num - (num - i);
            positionShips[cont][0] = num;
            positionShips[cont][1] = (int) (Math.random() * 10);
            positionShips[cont][2] = direc;
            cont++;
            if (i == 3 && comp)
            {
                i++;
                comp = false;
            }
        }
    }

    private void setShips()
    {
        boolean comp = true;
        ramdomPos();
        int cont = 5;
        for (int i = 0; i < 5; i++)
        {
            if (positionShips[i][2] == 0)
                for (int j = positionShips[i][0]; j < positionShips[i][0] + cont; j++)
                    board[positionShips[i][1]][j] = '+';
            else
                for (int j = positionShips[i][0]; j < positionShips[i][0] + cont; j++)
                    board[j][positionShips[i][1]] = '+';
            if (cont == 3 && comp)
            {
                cont++;
                comp = false;
            }
            cont--;
        }
    }

    private void setImages()
    {
        boolean comp = true;
        int cont = 5;
        for (int i = 0; i < 5; i++)
        {
            if (positionShips[i][2] == 0)
            {
                int pos = getBoardPost(positionShips[i][1], positionShips[i][0]);
                switch (cont)
                {
                    case 5:
                        battleship = new Battleship(Load.LoadImage("Battleship.png"), 5, ships.boardEnemy.boxBoard[pos][0], ships.boardEnemy.boxBoard[pos][2]);
                        break;
                    case 4:
                        cruise = new Cruise(Load.LoadImage("Cruise.png"), 4, ships.boardEnemy.boxBoard[pos][0], ships.boardEnemy.boxBoard[pos][2]);
                        break;
                    case 3:
                        if (comp)
                            destroyer1 = new Destroyer(Load.LoadImage("Destroyer.png"), 3, ships.boardEnemy.boxBoard[pos][0], ships.boardEnemy.boxBoard[pos][2]);
                        else
                            destroyer2 = new Destroyer(Load.LoadImage("Destroyer.png"), 3, ships.boardEnemy.boxBoard[pos][0], ships.boardEnemy.boxBoard[pos][2]);
                        break;
                    case 2:
                        submarine = new Submarine(Load.LoadImage("Submarine.png"), 2, ships.boardEnemy.boxBoard[pos][0], ships.boardEnemy.boxBoard[pos][2]);
                        break;
                }
            } else
            {
                int pos = getBoardPost(positionShips[i][0], positionShips[i][1]);
                switch (cont)
                {
                    case 5:
                        battleship = new Battleship(Load.LoadImage("Battleship.png"), 5, ships.boardEnemy.boxBoard[pos][0], ships.boardEnemy.boxBoard[pos][2]);
                        battleship.rotateTo90();
                        break;
                    case 4:
                        cruise = new Cruise(Load.LoadImage("Cruise.png"), 4, ships.boardEnemy.boxBoard[pos][0], ships.boardEnemy.boxBoard[pos][2]);
                        cruise.rotateTo90();
                        break;
                    case 3:
                        if (comp)
                        {
                            destroyer1 = new Destroyer(Load.LoadImage("Destroyer.png"), 3, ships.boardEnemy.boxBoard[pos][0], ships.boardEnemy.boxBoard[pos][2]);
                            destroyer1.rotateTo90();
                        } else
                        {
                            destroyer2 = new Destroyer(Load.LoadImage("Destroyer.png"), 3, ships.boardEnemy.boxBoard[pos][0], ships.boardEnemy.boxBoard[pos][2]);
                            destroyer2.rotateTo90();
                        }
                        break;
                    case 2:
                        submarine = new Submarine(Load.LoadImage("Submarine.png"), 2, ships.boardEnemy.boxBoard[pos][0], ships.boardEnemy.boxBoard[pos][2]);
                        submarine.rotateTo90();
                        break;
                }
            }
            if (cont == 3 && comp)
            {
                cont++;
                comp = false;
            }
            cont--;
        }

    }

    @Override
    public String toString()
    {
        String xd = "";
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
                xd += board[i][j];
            xd += "\n";
        }
        return xd;
    }

    @Override
    public void draw(Graphics g)
    {
        if (battleship.getLife() == 0)
            battleship.draw(g);
        if (cruise.getLife() == 0)
            cruise.draw(g);
        if (destroyer1.getLife() == 0)
            destroyer1.draw(g);
        if (destroyer2.getLife() == 0)
            destroyer2.draw(g);
        if (submarine.getLife() == 0)
            submarine.draw(g);
    }
}
