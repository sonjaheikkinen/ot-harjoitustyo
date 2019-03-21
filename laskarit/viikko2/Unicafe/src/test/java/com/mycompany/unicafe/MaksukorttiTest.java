package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertTrue(kortti.saldo() == 10);
    }
    
    @Test
    public void rahanLatausToimiiOikein() {
        kortti.lataaRahaa(10);
        assertTrue(kortti.saldo() == 20);
    }
    
    @Test
    public void rahaaOtetaanOikeaMaara() {
        kortti.otaRahaa(5);
        assertTrue(kortti.saldo() == 5);
    }
    
    @Test
    public void rahaaEiOtetaJosMenisiMiinukselle() {
        kortti.otaRahaa(20);
        assertTrue(kortti.saldo() == 10);
    }
    
    @Test
    public void otaRahaaPalauttaaTrueJosRahanOttaminenOnnistuu() {
        assertEquals(true, kortti.otaRahaa(5));
    }
    
    @Test
    public void otaRahaaPalauttaaFalseJosRahanOttaminenEiOnnistu() {
        assertEquals(false, kortti.otaRahaa(20));
    }
    
    @Test
    public void toStringToimiiOikein() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
}
