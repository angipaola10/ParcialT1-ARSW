package edu.eci.arsw.api.primesrepo.service;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;
import edu.eci.arsw.api.primesrepo.model.PrimeException;
import java.math.BigInteger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
@Service("PrimeServiceStub")
public class PrimeServiceStub implements PrimeService
{
    private List<FoundPrime> foundPrimes = new CopyOnWriteArrayList<>();

    public PrimeServiceStub() {
        foundPrimes.add(new FoundPrime("Angi", "17"));
        foundPrimes.add(new FoundPrime("Paola", "13"));
    }
    
    @Override
    public synchronized void addFoundPrime( FoundPrime foundPrime ) throws PrimeException
    {
        for (FoundPrime fp: foundPrimes){
            if (fp.getPrime().equals(foundPrime.getPrime())){
                throw new PrimeException("Prime alreasy exists");
            }
        }
        foundPrimes.add(foundPrime);
   
    }

    @Override
    public List<FoundPrime> getFoundPrimes()
    {
        return foundPrimes;
    }

    @Override
    public FoundPrime getPrime( String prime ) throws PrimeException
    {
        for (FoundPrime fp: foundPrimes){
            if (fp.getPrime().equals(prime)){
                return fp;
            }
        }
        throw new PrimeException("Prime not found");
    }
}
