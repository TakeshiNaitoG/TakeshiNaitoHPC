package cl.ucn.disc.hpc.primes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.time.StopWatch;

import org.slf4j.ILoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Main
 * @author Takeshi Naito Galleguillos
 **/
public class Main {

    private static final Logger log= LoggerFactory.getLogger(Main.class);

    /**
     * The Main
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {


        //Maximo
        final long MAX=20;

        //Cronometro
        final StopWatch stopWatch= StopWatch.createStarted();



        log.debug("start The Main..");

        /**
         * EL ejecutador
         */
        final ExecutorService executorService= Executors.newFixedThreadPool(16);
        //
        for(long i=1;i<MAX;i++){

            executorService.submit(new PrimeTask(i));e
        }
        //no realixa mas tareas
        executorService.shutdown();

        if(executorService.awaitTermination(1, TimeUnit.HOURS)){
            log.debug("Primes found: {} in {}.",PrimeTask.getPrimes(),stopWatch);
        }else{
            //tiempo:
            log.info("Done in {}.",stopWatch);
        }



    }
    private static class PrimeTask implements Runnable{

        private final long number;

        public PrimeTask(final long number){
            this.number=number;
        }

        /**
         * Contador
         */
        private final static AtomicInteger counter= new AtomicInteger(0);


        //retorna los numeros primos.
        public static int  getPrimes(){
            return counter.get();
        }

        /**
         * Run the code.
         */
        public void run(){


            //si es primo contamos
            if(isPrime(this.number)){
                //log.debug("{} es primo!!!",this.number);
                counter.getAndIncrement();
                System.out.print("estoy en el run");

            }

        }

        private static boolean isPrime(final long n){

            // Detectanto que no sea negativo
            if (n <= 0){
                throw new IllegalArgumentException("Error in n: No puede ser negativo");
            }
            //EL uno no es primo
            if (n == 1){
                return false;
            }
            //probando si es primo desde el 2 hasta n-1
            //TODO: cambiar n/2 the upper list
            for(long i =2;i<n;i++){
                //si el modulo es = 0 no es primo
                if( n%i ==0){
                    return false;
                }
            }
            return true;
        }

    }

}
