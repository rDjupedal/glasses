# GlassTime
Programmet räknar ut efter hur lång tid ett visst glas är fyllt med vätska, där glaset 
tillsammans med andra glas bildar en tvådimensionell pyramidform.
Längst upp i pyramiden (rad 1) finns enbart ett glas, på raden under (rad 2) finns två 
glas, rad 3 har 3 glas osv.
Vätska hälls i det översta glaset med en konstant hastighet (1/10 glas per sekund). 
När glaset är fyllt rinner det sedan nedan i de två undre glasen. 

              \/                <-- Rad 1, fyllt efter 10 s
            \/  \/              <-- Rad 2, fyllda efter 30 s
          \/  \/  \/            <-- Rad 3, fyllda efter 50 s (mitten) respektive 70 s
        \/  \/  \/  \/
      \/  \/  \/  \/  \/
    \/  \/  \/  \/  \/  \/

## Ladda ner och köra programmet
```git clone https://github.com/rDjupedal/glasses```  
```cd GlassTime/src/main/java```  
```javac glasstime/GlassTime.java```  
```java glasstime.GlassTime```