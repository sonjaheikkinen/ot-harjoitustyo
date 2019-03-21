/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    
    private Kassapaate kassa;
    private Maksukortti kortti;
    private Maksukortti koyhaKortti;
    
    public KassapaateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.kassa = new Kassapaate();
        this.kortti = new Maksukortti(500);
        this.koyhaKortti = new Maksukortti(100);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void kassaPaatteessaOikeaMaaraRahaa() {
        assertTrue(kassa.kassassaRahaa() == 1000);
    }
    
    @Test
    public void myytyjaEdullisiaLounaitaAluksiNolla() {
        assertTrue(kassa.edullisiaLounaitaMyyty() == 0);
    }
    
    @Test
    public void myytyjaMaukkaitaLounaitaAluksiNolla() {
        assertTrue(kassa.maukkaitaLounaitaMyyty() == 0);
    }
    
    @Test
    public void syoEdullisestiKasvattaaRahamaaraaOikeinJosKateistaRiittavasti() {
        kassa.syoEdullisesti(500);
        assertTrue(kassa.kassassaRahaa() == 1240);
    }
    
    @Test
    public void syoEdullisestiAntaaOikeanMaaranVaihtorahaaJosKateistaRiittavasti() {
        assertEquals(260, kassa.syoEdullisesti(500));    
    }
    
    @Test
    public void syoEdullisestiKasvattaaMyytyjenLounaidenMaaraaJosKateistaRiittavasti() {
        kassa.syoEdullisesti(500);
        assertTrue(kassa.edullisiaLounaitaMyyty() == 1);
    }
    
    @Test
    public void syoEdullisestiEiKasvataKassanRahamaaraaJosKateinenEiRiita() {
        kassa.syoEdullisesti(200);
        assertTrue(kassa.kassassaRahaa() == 1000);
    }
    
    @Test
    public void syoEdullisestiPalauttaaKaikkiRahatJosKateinenEiRiita() {
        assertEquals(200, kassa.syoEdullisesti(200));
    }
    
    @Test
    public void syoEdullisestiEiKasvataMyytyjenLounaidenMaaraaJosKateinenEiRiita() {
        kassa.syoEdullisesti(200);
        assertTrue(kassa.edullisiaLounaitaMyyty() == 0);
    }
    
     
    @Test
    public void syoMaukkaastiKasvattaaRahamaaraaOikeinJosKateistaRiittavasti() {
        kassa.syoMaukkaasti(500);
        assertTrue(kassa.kassassaRahaa() == 1400);
    }
    
    @Test
    public void syoMaukkaastiAntaaOikeanMaaranVaihtorahaaJosKateistaRiittavasti() {
        assertEquals(100, kassa.syoMaukkaasti(500));    
    }
    
    @Test
    public void syoMaukkaastiKasvattaaMyytyjenLounaidenMaaraaJosKateistaRiittavasti() {
        kassa.syoMaukkaasti(500);
        assertTrue(kassa.maukkaitaLounaitaMyyty() == 1);
    }
    
    @Test
    public void syoMaukkaastiEiKasvataKassanRahamaaraaJosKateinenEiRiita() {
        kassa.syoMaukkaasti(200);
        assertTrue(kassa.kassassaRahaa() == 1000);
    }
    
    @Test
    public void syoMaukkaastiPalauttaaKaikkiRahatJosKateinenEiRiita() {
        assertEquals(200, kassa.syoMaukkaasti(200));
    }
    
    @Test
    public void syoMaukkaastiEiKasvataMyytyjenLounaidenMaaraaJosKateinenEiRiita() {
        kassa.syoMaukkaasti(200);
        assertTrue(kassa.maukkaitaLounaitaMyyty() == 0);
    }
    
    @Test
    public void syoEdullisestiVahentaaEdullisenHinnanKortiltaJosKortillaRiittavastiRahaa() {
        kassa.syoEdullisesti(kortti);
        assertEquals(260, kortti.saldo());
    }
    
    @Test
    public void syoEdullisestiPalauttaaTrueJosKortillaRiittavastiRahaa() {
        assertEquals(true, kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void syoEdullisestiKasvattaaMyytyjenEdullistenLounaidenMaaraaJosKortillaRiittavastiRahaa() {
        kassa.syoEdullisesti(kortti);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiEiVeloitaKorttiaJosKortinRahamaaraEiRiita() {
        kassa.syoEdullisesti(koyhaKortti);
        assertTrue(koyhaKortti.saldo() == 100);
    }
    
    @Test
    public void syoEdullisestiEiKasvataEdullistenLounaidenMaaraaJosKortinRahamaaraEiRiita() {
        kassa.syoEdullisesti(koyhaKortti);
        assertTrue(kassa.edullisiaLounaitaMyyty() == 0);
    }
    
    @Test
    public void syoEdullisestiPalauttaaFalseJosKortinRahamaaraEiRiita() {
        assertEquals(false, kassa.syoEdullisesti(koyhaKortti));
    }
    
    @Test
    public void syoEdullisestiEiMuutaKassanRahamaaraa() {
        kassa.syoEdullisesti(kortti);
        assertEquals(1000, kassa.kassassaRahaa());
    }
        
    @Test
    public void syoMaukkaastiVahentaaMaukkaanHinnanKortiltaJosKortillaRiittavastiRahaa() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(100, kortti.saldo());
    }
    
    @Test
    public void syoMaukkaastiPalauttaaTrueJosKortillaRiittavastiRahaa() {
        assertEquals(true, kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void syoMaukkaastiKasvattaaMyytyjenMaukkaidenLounaidenMaaraaJosKortillaRiittavastiRahaa() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiEiVeloitaKorttiaJosKortinRahamaaraEiRiita() {
        kassa.syoMaukkaasti(koyhaKortti);
        assertTrue(koyhaKortti.saldo() == 100);
    }
    
    @Test
    public void syoMaukkaastiEiKasvataMaukkaidenLounaidenMaaraaJosKortinRahamaaraEiRiita() {
        kassa.syoMaukkaasti(koyhaKortti);
        assertTrue(kassa.maukkaitaLounaitaMyyty() == 0);
    }
    
    @Test
    public void syoMaukkaastiPalauttaaFalseJosKortinRahamaaraEiRiita() {
        assertEquals(false, kassa.syoMaukkaasti(koyhaKortti));
    }
    
    @Test
    public void syoMaukkaastiEiMuutaKassanRahamaaraa() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(1000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kortinLataaminenKasvattaaKortinSaldoaOikeanMaaran() {
        kassa.lataaRahaaKortille(kortti, 500);
        assertEquals(1000, kortti.saldo());
    }
    
     @Test
     public void kortinLataaminenKasvattaaKassanRahamaaraaOikeanMaaran() {
         kassa.lataaRahaaKortille(kortti, 500);
         assertEquals(1500, kassa.kassassaRahaa());
     }
     
     @Test
    public void kortinLataaminenEiKasvataKortinSaldoaJosLadataanNegatiivinenSumma() {
        kassa.lataaRahaaKortille(kortti, -10);
        assertEquals(500, kortti.saldo());
    }
    
     @Test
     public void kortinEiKasvataKassanRahamaaraaJosLadataanNegatiivinenSumma() {
         kassa.lataaRahaaKortille(kortti, -10);
         assertEquals(1000, kassa.kassassaRahaa());
     }
}
