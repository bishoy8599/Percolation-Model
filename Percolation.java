/* *****************************************************************************
 *  Name:             Bishoy Sabry
 *  Last modified:     October 16, 2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid;
    private int[] state;
    private int N;
    private int nsq;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("index " + n + " is not greater than 0" );
        }
        N=n;
        nsq = n * n;
        grid = new WeightedQuickUnionUF(nsq);
        state = new int[nsq];
        //for (int i = 0; i < nsq; i++) {
       //     state[i] = 0;
      //  }      //0 is blocked , 1 is open
        for (int i = 1; i < n; i++) {
            grid.union(0, i);
            grid.union(nsq-1,nsq-1-i);
       }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (N>1) {
            if (row >= 1 && row <= N && col >= 1 && col <= N) {
                state[(((row - 1) * N) + col - 1)] = 1;
                if (row == 1) {
                    if (state[(((row) * N) + col - 1)] == 1) {
                        grid.union((((row) * N) + col - 1), (((row - 1) * N) + col - 1));
                    }
                }
                   // for (int i = 1; i < N; i++) {
                    //    if (state[i]==1 && i!=(((row - 1) * N) + col - 1)){
                   //         grid.union(i, (((row - 1) * N) + col - 1));
                    //    }


                else if (row == N) {
                    if (state[(((row - 2) * N) + col - 1)] == 1) {
                        grid.union((((row - 2) * N) + col - 1), (((row - 1) * N) + col - 1));
                    }
                }
                if (col == N) {
                    if (state[(((row - 1) * N) + col - 2)] == 1) {
                        grid.union((((row - 1) * N) + col - 2), (((row - 1) * N) + col - 1));
                    }
                    if (row != 1 && row != N) {
                        if (state[(((row) * N) + col - 1)] == 1) {
                            grid.union((((row) * N) + col - 1), (((row - 1) * N) + col - 1));
                        }
                        if (state[(((row - 2) * N) + col - 1)] == 1) {
                            grid.union((((row - 2) * N) + col - 1), (((row - 1) * N) + col - 1));
                        }
                    }
                }
                else if (col == 1) {
                    if (state[(((row - 1) * N) + col)] == 1) {
                        grid.union((((row - 1) * N) + col), (((row - 1) * N) + col - 1));
                    }
                    if (row != 1 && row != N) {
                        if (state[(((row) * N) + col - 1)] == 1) {
                            grid.union((((row) * N) + col - 1), (((row - 1) * N) + col - 1));
                        }
                        if (state[(((row - 2) * N) + col - 1)] == 1) {
                            grid.union((((row - 2) * N) + col - 1), (((row - 1) * N) + col - 1));
                        }
                    }
                }
                if (row != 1 && row != N && col != 1 && col != N) {
                    if (state[(((row) * N) + col - 1)] == 1) {
                        grid.union((((row) * N) + col - 1), (((row - 1) * N) + col - 1));
                    }
                    if (state[(((row - 2) * N) + col - 1)] == 1) {
                        grid.union((((row - 2) * N) + col - 1), (((row - 1) * N) + col - 1));
                    }
                    if (state[(((row - 1) * N) + col)] == 1) {
                        grid.union((((row - 1) * N) + col), (((row - 1) * N) + col - 1));
                    }
                    if (state[(((row - 1) * N) + col - 2)] == 1) {
                        grid.union((((row - 1) * N) + col - 2), (((row - 1) * N) + col - 1));
                    }
                }
            } else {
                throw new IllegalArgumentException(
                        "index " + row + "or" + col + " is not between 0 and " + (N - 1));
            }
        }else if (N==1){ state[0]=1;}

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row >= 1 && row <= N && col >= 1 && col <= N) {
            return (state[(((row - 1) * N) + col - 1)] == 1);
        }
        else {
            throw new IllegalArgumentException(
                    "index " + row + "or" + col + " is not between 0 and " + (N - 1));
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int top=0;
        boolean check=false;

        if (row >= 1 && row <= N && col >= 1 && col <= N) {
            if (this.isOpen(row, col)) {
                for (int i=0; i < N; i++) {
                    if (state[i] == 1) {
                        top = i;
                        check = true;
                        break;
                    }
                }
                if (check) {
                    return (grid.find((((row - 1) * N) + col-1)) == grid.find(top));
                }
                else {
                    return false;
                }
            }else {return false;}
        }
        else {
            throw new IllegalArgumentException(
                    "index " + row + "or" + col + " is not between 0 and " + (N - 1));
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int counter = 0;
        for (int i = 0; i < nsq; i++) {
            if (state[i] == 1) {
                counter++;
            }
        }
        return counter;
    }

    // does the system percolate?
    public boolean percolates() {
        int top =0, bottom = 0;
        boolean check1 =false , check2 = false;
        for (int i = 0; i < N; i++) {
            if (state[i] == 1) {
                top = i;
                check1 = true;
            }
            if (state[nsq - 1 - i] == 1) {
                bottom = nsq - 1 - i;
                check2 = true;
            }
        }
        if (check1 && check2) {
            return (grid.find(top) == grid.find((bottom)));
        }
        else {
            return false;
        }
    }
    // test client (optional)
    public static void main(String[] args) {
        int m = StdIn.readInt();
        Percolation uf = new Percolation(m);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            uf.open(p, q);
            System.out.println(p + " , " + q);
        }
        int numopen= uf.numberOfOpenSites();
        if (uf.percolates()) {
            System.out.println("the system percolates");
            System.out.println("number of open sites= " + numopen );
        }
        else {
            System.out.println("the system doesnt percolate");
            System.out.println("number of open sites= " + numopen );
        }
    }
}