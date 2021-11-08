/* *****************************************************************************
 *  Name:             Bishoy Sabry
 *  Last modified:     October 16, 2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int[] opennum;
    private int trial;
    private int nsq ;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("index: " + n +" or number of trials: "+ trials + " is not greater than 0" );
        }
         trial = trials;
         nsq = n * n;
        int rowrand, colrand;
        opennum = new int[trial];

        for (int j = 0; j < trials; j++) {
            Percolation uf = new Percolation(n);
            for (int i = 0; i < nsq; i++) {
                rowrand = StdRandom.uniform(1, n);
                colrand = StdRandom.uniform(1, n);
                if (!uf.isOpen(rowrand, colrand)) {
                    uf.open(rowrand, colrand);
                    opennum[j]=opennum[j]+1;
                }
                if (uf.percolates()) {
                    //opennum[j] = uf.numberOfOpenSites();
                    break;
                }
            }
        }
    }
    // sample mean of percolation threshold
    public double mean(){
            return (StdStats.mean(opennum)/nsq);
        }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return (StdStats.stddev(opennum)/nsq);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return ((this.mean())-((1.96 *(this.stddev()))/Math.sqrt(trial)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return ((this.mean())+((1.96 *(this.stddev()))/Math.sqrt(trial)));
    }

    // test client (see below)
    public static void main(String[] args){
        int trials = Integer.parseInt(args[0]);
        int n =  Integer.parseInt(args[1]);
        PercolationStats ufst = new PercolationStats(n,trials) ;
        System.out.println("Mean = " + ufst.mean());
        System.out.println("stddev = "  + ufst.stddev());
        System.out.println("95% confidence interval = [" + ufst.confidenceLo() + " , "+ ufst.confidenceHi()+"]");

    }

}