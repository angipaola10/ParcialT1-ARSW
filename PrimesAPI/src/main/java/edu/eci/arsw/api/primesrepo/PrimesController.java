package edu.eci.arsw.api.primesrepo;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;
import edu.eci.arsw.api.primesrepo.model.PrimeException;
import edu.eci.arsw.api.primesrepo.service.PrimeService;
import edu.eci.arsw.api.primesrepo.service.PrimeServiceStub;
import java.lang.reflect.MalformedParametersException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
@RestController
@RequestMapping(value = "/primes")
public class PrimesController
{
    @Autowired
    @Qualifier("PrimeServiceStub")
    PrimeService primeService;
    
    @RequestMapping(method = RequestMethod.GET)
     public ResponseEntity<?> getPrimes()
    {   
        try{
            return new ResponseEntity<>(primeService.getFoundPrimes(), HttpStatus.ACCEPTED);
        } catch (MalformedParametersException e) {
            Logger.getLogger(PrimesController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
     
     
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addSuspectAccounts(@RequestBody FoundPrime foundPrime)
    {
        try {
            primeService.addFoundPrime(foundPrime);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (PrimeException e) {
            Logger.getLogger(PrimesController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value="/{primeNumber}",method = RequestMethod.GET)
    public ResponseEntity<?> getAccountByID(@PathVariable String primeNumber)
    {
        try {
            return new ResponseEntity<>(primeService.getPrime(primeNumber), HttpStatus.ACCEPTED);
        } catch (PrimeException e) {
            Logger.getLogger(PrimesController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
