import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;


class Legesystem{

  protected Lenkeliste<Pasient> pasienter;
  protected Lenkeliste<Legemiddel> legemidler;
  protected Lenkeliste<Lege> leger;
  protected Lenkeliste<Resept> resepter;
  private Scanner inp;
  private int inputTall;
  private String inputStreng;

  public static void main(String[] args){
    Legesystem system = new Legesystem();
    system.lesFil("myeInndata.txt");
    system.hovedmeny();
  }

  //Konstruktør
  public Legesystem(){
    pasienter = new Lenkeliste();
    legemidler = new Lenkeliste();
    leger = new SortertLenkeliste();
    resepter = new Lenkeliste();
  }

  //Tar imot en streng på et filnavn og legger inn data i systemet
  public void lesFil(String filnavn){
    int type = 0;
    int feilLegemiddel = 0;
    int feilResept = 0;

    try{
      File fil = new File(filnavn);
      inp = new Scanner(fil);
    }
    catch(FileNotFoundException e){
      System.out.println("Finner ikke fil med navn "+filnavn);
      return;
    }

    //Løkken kjører så lenge det finnes en ny linje i filen
    while(inp.hasNextLine()){
      String linje = inp.nextLine();
      if(linje.startsWith("#")){
        type++;
      }

      else{
        String[] biter = linje.trim().split(",");

        //Den første typen objekter som lages er pasienter
        if(type==1){
          Pasient pas = new Pasient(biter[0], biter[1]);
          pasienter.leggTil(pas);
        }

        //Den andre typen objekter som lages er legemidler
        else if(type==2){
          try{
            if(biter[1].equals("narkotisk")){
              Narkotisk nark = new Narkotisk(biter[0], Double.parseDouble(biter[2]), Double.parseDouble(biter[3]), Integer.parseInt(biter[4]));
              legemidler.leggTil(nark);
            }

            else if(biter[1].equals("vanedannende")){
              Vanedannende vane = new Vanedannende(biter[0], Double.parseDouble(biter[2]), Double.parseDouble(biter[3]), Integer.parseInt(biter[4]));
              legemidler.leggTil(vane);
            }

            else{
              Vanlig vanlig = new Vanlig(biter[0], Double.parseDouble(biter[2]), Double.parseDouble(biter[3]));
              legemidler.leggTil(vanlig);
            }
          }
          catch(NumberFormatException e){
            feilLegemiddel++;
          }
        }

        //Den tredje typen objekter som lages er leger
        else if(type==3){
          if(biter[1].equals("0")){
            Lege l = new Lege(biter[0]);
            leger.leggTil(l);
          }
          else{
            Spesialist s = new Spesialist(biter[0], Integer.parseInt(biter[1]));
            leger.leggTil(s);
          }
        }

        //Den siste typen objekter som lages er resepter
        else if(type==4){
          Legemiddel lm = null;
          Pasient p = null;
          Lege l = null;
          String typeResept = biter[3];
          Resept r = null;

          //Disse tre løkkene sjekker om det det finnes et objekt med riktig ID/navn for de respektive typene
          for(Legemiddel legemiddel:legemidler){
            if (legemiddel.hentId()==Integer.parseInt(biter[0])){
              lm = legemiddel;
            }
          }

          for(Lege lege:leger){
            if (lege.hentNavn().equals(biter[1])){
              l = lege;
            }
          }

          for(Pasient pasient:pasienter){
            if (pasient.hentId()==Integer.parseInt(biter[2])){
              p = pasient;
            }
          }

          //Det finnes fire forskjellige typer resepter som kan lages
          try{
            if (typeResept.equals("hvit")){
              r = l.skrivHvitResept(lm, p, Integer.parseInt(biter[4]));
              resepter.leggTil(r);
            }

            else if (typeResept.equals("blaa")){
              r = l.skrivBlaaResept(lm, p, Integer.parseInt(biter[4]));
              resepter.leggTil(r);
            }

            else if (typeResept.equals("militaer")){
                r = l.skrivMilitaerResept(lm, p, Integer.parseInt(biter[4]));
                resepter.leggTil(r);
            }

            else if (typeResept.equals("p")){
                r = l.skrivPResept(lm, p);
                resepter.leggTil(r);
            }
          }
          catch(NullPointerException | UlovligUtskrift e){
            feilResept++;
          }
        }
      }
    }

    //Skriver ut info om hva slags objekter som ble laget
    System.out.println();
    System.out.println(pasienter.stoerrelse()+" pasienter ble lagt inn i systemet.");
    System.out.println(legemidler.stoerrelse()+" legemidler ble lagt inn i systemet.");
    System.out.println(leger.stoerrelse()+" leger ble lagt inn i systemet.");
    System.out.println(resepter.stoerrelse()+" resepter ble lagt inn i systemet.\n");
    System.out.println(feilLegemiddel+" legemidler ble ikke opprettet paa grunn av feil format.");
    System.out.println(feilResept+" resepter ble ikke opprettet paa grunn av feil format eller ulovlig utskrift av utskrivende lege.\n");
  }

  public void skrivUtHovedmeny(){
    System.out.println("\nHovedmeny: ");
    System.out.println("1: Skriv ut oversikt over pasienter, leger, legemidler og resepter.");
    System.out.println("2: Legg til nye elementer i systemet.");
    System.out.println("3: Bruk en resept.");
    System.out.println("4: Skriv ut statistikk.");
    System.out.println("5: Skriv alle data til en fil.");
    System.out.println("0: Avslutt.");
  }

  //Hovedmenyen
  public void hovedmeny(){
    Boolean ferdig = false;
    inp = new Scanner(System.in);

    while(!ferdig){
      skrivUtHovedmeny();
      System.out.print("Velg handling: >");
      try{
        inputTall = inp.nextInt();
        if(inputTall == 1){
          skrivUtOversikt();
        }
        else if(inputTall == 2){
          leggTilElement();
        }
        else if(inputTall == 3){
          brukResept();
        }
        else if(inputTall == 4){
          skrivUtStatistikk();
        }
        else if(inputTall == 5){
          skrivTilFil();
        }
        else if(inputTall == 0){
          ferdig = true;
        }
      }
      catch(InputMismatchException e){
        System.out.println("Skriv et tall for aa velge handling.");
        tilbake();
      }
    }
  }

  //Skriver ut en oversikt over alle objektene i systemet
  public void skrivUtOversikt(){
    System.out.println("\nPasienter: \n");
    for(Pasient pasient:pasienter){
      System.out.println(pasient);
    }
    System.out.println("\nLegemidler: \n");
    for(Legemiddel lm:legemidler){
      System.out.println(lm);
    }
    System.out.println("\nLeger: \n");
    for(Lege lege:leger){
      System.out.println(lege);
    }
    System.out.println("\nResepter: \n");
    for(Resept resept:resepter){
      System.out.println(resept);
    }
    tilbake();
  }

  //Metode for å legge til et element i systemet, kaller på andre metoder for å opprette objekter
  public void leggTilElement(){
    inp = new Scanner(System.in);
    System.out.println("Hva slags objekt onsker du aa oprette? ");
    System.out.println("1: Lege");
    System.out.println("2: Legemiddel");
    System.out.println("3: Pasient");
    System.out.println("4: Resept");
    System.out.println("0: Gaa tilbake");
    try{
      inputTall = inp.nextInt();
    }
    catch(InputMismatchException e){
      System.out.println("Ikke gjenkjent handling, gaar tilbake til hovedmenyen.");
      tilbake();
      return;
    }
    if(inputTall==1){
      lagLege();
    }
    else if(inputTall==2){
      lagLegemiddel();
    }
    else if(inputTall==3){
      lagPasient();
    }
    else if(inputTall==4){
      lagResept();
    }
    else if(inputTall==0){
      System.out.println("Gaar tilbake til hovedmenyen.");
      tilbake();
    }
    else{
      System.out.println("Ikke gjenkjent handling, gaar tilbake til hovedmenyen.");
      tilbake();
    }
  }

  //Lager et legeobjekt og legger det inn i listen over leger
  private void lagLege(){
    Lege l = null;
    String navn;
    int kontrollId;
    inp = new Scanner(System.in);
    System.out.print("Hva er navnet til legen? ");
    navn = inp.nextLine();
    System.out.println("Er legen spesialist eller vanlig lege? (s/v)");

    try{
      inputStreng = inp.next();
    }
    catch(InputMismatchException e){
      System.out.println("Ikke gjenkjent handling, gaar tilbake til hovedmenyen.");
      tilbake();
      return;
    }

    if(inputStreng.equals("v")){
      l = new Lege(navn);
      System.out.println("Lege-objektet er opprettet.");
    }

    else if(inputStreng.equals("s")){
      System.out.print("Hva er kontroll-ID'en til spesialisten? ");
      try{
        kontrollId = inp.nextInt();
      }
      catch(InputMismatchException e){
        System.out.println("Ikke gjenkjent handling, gaar tilbake til hovedmenyen.");
        tilbake();
        return;
      }
      l = new Spesialist(navn, kontrollId);
      System.out.println("Spesialist-objektet er opprettet.");
    }
    else{
      System.out.println("Ikke gjenkjent handling, gaar tilbake til hovedmenyen.");
      tilbake();
      return;
    }
    tilbake();
    leger.leggTil(l);
  }

  //Lager et legemiddelobjekt og legger det til i listen over legemidler
  private void lagLegemiddel(){
    Legemiddel lm = null;
    String navn;
    Double mg;
    Double prs;
    int styrk;
    inp = new Scanner(System.in);
    System.out.print("Hva er navnet til legemiddelet? ");
    navn = inp.nextLine();
    System.out.print("Hvor mange mg virkestoff er det? ");
    try{
      mg = inp.nextDouble();
    }
    catch(InputMismatchException e){
      System.out.println("Ikke gjenkjent handling, gaar tilbake til hovedmenyen.");
      tilbake();
      return;
    }
    System.out.print("Hva er prisen? ");
    try{
      prs = inp.nextDouble();
    }
    catch(InputMismatchException e){
      System.out.println("Ikke gjenkjent handling, gaar tilbake til hovedmenyen.");
      tilbake();
      return;
    }
    System.out.println("Hva slags type legemiddel er det?");
    System.out.println("1: Vanlig");
    System.out.println("2: Vanedannende");
    System.out.println("3: Narkotisk");
    try{
      inputTall = inp.nextInt();
    }
    catch(InputMismatchException e){
      System.out.println("Ikke gjenkjent handling, gaar tilbake til hovedmenyen.");
      tilbake();
      return;
    }
    if(inputTall==1){
      lm = new Vanlig(navn, mg, prs);
      System.out.println("Legemiddelet er opprettet");
    }

    else if(inputTall==2){
      System.out.println("Hva er styrken til legemiddelet?");
      try{
        styrk = inp.nextInt();
      }
      catch(InputMismatchException e){
        System.out.println("Ikke gjenkjent handling, gaar tilbake til hovedmenyen.");
        tilbake();
        return;
      }
      lm = new Vanedannende(navn, mg, prs, styrk);
      System.out.println("Legemiddelet er opprettet");
    }

    else if(inputTall==3){
      System.out.println("Hva er styrken til legemiddelet?");
      try{
        styrk = inp.nextInt();
      }
      catch(InputMismatchException e){
        System.out.println("Ikke gjenkjent handling, gaar tilbake til hovedmenyen.");
        tilbake();
        return;
      }
      lm = new Narkotisk(navn, mg, prs, styrk);
      System.out.println("Legemiddelet er opprettet");
    }
    else{
      System.out.println("Ikke gjenkjent handling, gaar tilbake til hovedmenyen.");
      tilbake();
      return;
    }

    legemidler.leggTil(lm);
    tilbake();
  }

  //Lager et pasientobjekt og legger det til i listen over pasienter
  private void lagPasient(){
    Pasient p = null;
    String navn;
    String fnr;
    inp = new Scanner(System.in);
    System.out.print("Hva er navnet til pasienten? ");
    navn = inp.nextLine();
    System.out.print("Hva er fodselsnummeret til pasienten? ");
    fnr = inp.nextLine();
    p = new Pasient(navn, fnr);
    System.out.println("Pasient-objektet er opprettet");
    pasienter.leggTil(p);
    tilbake();
  }

  //Lager et reseptobjekt og legger det til i listen over resepter
  private void lagResept(){
    Resept r = null;
    Legemiddel lm = null;
    Lege l = null;
    Pasient p = null;
    int reit = 0;
    inp = new Scanner(System.in);
    System.out.println("Hvilket legemiddel skal resepten vaere paa?");
    System.out.print("Skriv navnet paa legemiddelet: ");
    inputStreng = inp.nextLine();
    for(Legemiddel legemiddel:legemidler){
      if(inputStreng.equals(legemiddel.hentNavn())){
        lm = legemiddel;
      }
    }
    if(lm==null){
      System.out.println("Fant ikke legemiddelet, gaar tilbake til hovedmenyen.");
      tilbake();
      return;
    }

    System.out.println("Hvilken lege skal skrive ut resepten?");
    System.out.print("Skriv navnet paa legen: ");
    inputStreng = inp.nextLine();
    for(Lege lege:leger){
      if(inputStreng.equals(lege.hentNavn())){
        l = lege;
      }
    }
    if(l==null){
      System.out.println("Fant ikke legen, gaar tilbake til hovedmenyen.");
      tilbake();
      return;
    }

    System.out.println("Hvilken pasient skal resepten vaere for?");
    System.out.print("Skriv navnet paa pasienten: ");
    inputStreng = inp.nextLine();
    for(Pasient pasient:pasienter){
      if(inputStreng.equals(pasient.hentNavn())){
        p = pasient;
      }
    }
    if(p==null){
      System.out.println("Fant ikke pasienten, gaar tilbake til hovedmenyen.");
      tilbake();
      return;
    }

    System.out.println("Hva slags resept onsker du aa lage?");
    System.out.println("1: Hvit resept");
    System.out.println("2: Blaa resept");
    System.out.println("3: Militaerresept");
    System.out.println("4: P-resept");

    try{
      inputTall = inp.nextInt();
    }
    catch(InputMismatchException e){
      System.out.println("Ikke gjenkjent handling, gaar tilbake til hovedmeny.");
      tilbake();
      return;
    }

    if(inputTall >=1 && inputTall <=3){
      System.out.println("Hvor mange reit skal resepten ha?");
      try{
        reit = inp.nextInt();
      }
      catch(InputMismatchException e){
        System.out.println("Ikke gjenkjent handling, gaar tilbake til hovedmenyen.");
        tilbake();
        return;
      }
    }

    if(inputTall==1){
      try{
        r = l.skrivHvitResept(lm,p,reit);
      }
      catch(UlovligUtskrift e){
        System.out.println("Denne legen kan ikke skrive ut dette legemiddelet. Gaar tilbake til hovedmenyen");
        tilbake();
        return;
      }
    }

    else if(inputTall==2){
      try{
        r = l.skrivBlaaResept(lm,p,reit);
      }
      catch(UlovligUtskrift e){
        System.out.println("Denne legen kan ikke skrive ut dette legemiddelet. Gaar tilbake til hovedmenyen");
        tilbake();
        return;
      }
    }

    else if(inputTall==3){
      try{
        r = l.skrivMilitaerResept(lm,p,reit);
      }
      catch(UlovligUtskrift e){
        System.out.println("Denne legen kan ikke skrive ut dette legemiddelet. Gaar tilbake til hovedmenyen");
        tilbake();
        return;
      }
    }

    else if(inputTall==4){
      try{
        r = l.skrivPResept(lm,p);
      }
      catch(UlovligUtskrift e){
        System.out.println("Denne legen kan ikke skrive ut dette legemiddelet. Gaar tilbake til hovedmenyen");
        tilbake();
        return;
      }
    }
    System.out.println("Resepten ble opprettet");
    resepter.leggTil(r);
    tilbake();
  }

  //Metode for å bruke en resept
  public void brukResept(){
    Pasient valgtPasient;
    Lenkeliste<Resept> reseptListe;
    for(int i =0; i<pasienter.stoerrelse();i++){
      System.out.println(i+": "+pasienter.hent(i));
    }
    System.out.println("Hvilken pasient vil du se resepter for?");
    System.out.print("Velg pasient: >");
    inp = new Scanner(System.in);
    try{
      inputTall = inp.nextInt();
    }
    catch(InputMismatchException e){
      System.out.println("Skriv et tall for aa velge pasient. Gaar tilbake til hovedmenyen.");
      tilbake();
      return;
    }

    if(inputTall > pasienter.stoerrelse()-1 || inputTall < 0){
      System.out.println("Ikke gjenkjent handling, gaar tilbake til hovedmeny.");
      tilbake();
      return;
    }
    valgtPasient = pasienter.hent(inputTall);
    reseptListe = valgtPasient.hentResepter();

    System.out.println("Valgt pasient: "+valgtPasient);

    if(reseptListe.stoerrelse() == 0){
      System.out.println("Denne pasienten har ingen resepter.");
      tilbake();
      return;
    }

    System.out.println("\nHvilken resept vil du bruke? ");
    for(int i =0; i<reseptListe.stoerrelse();i++){
      System.out.println(i+": "+reseptListe.hent(i).hentLegemiddel().hentNavn() + " ("+reseptListe.hent(i).hentReit()+" reit)");
    }
    System.out.print("Velg resept: >");
    try{
      inputTall = inp.nextInt();
    }
    catch(InputMismatchException e){
      System.out.println("Skriv et tall for aa velge resept. Gaar tilbake til hovedmenyen.");
      tilbake();
      return;
    }

    if(inputTall > valgtPasient.hentResepter().stoerrelse()-1 || inputTall < 0){
      System.out.println("Ikke gjenkjent handling, gaar tilbake til hovedmeny.");
      tilbake();
      return;
    }

    if(reseptListe.hent(inputTall).bruk()){
      System.out.println("Brukte resept paa "+reseptListe.hent(inputTall).hentLegemiddel().hentNavn()+". Antall gjenvaerende reit: "+reseptListe.hent(inputTall).hentReit());
      tilbake();
    }
    else{
      System.out.println("Kunne ikke bruke resept paa "+reseptListe.hent(inputTall).hentLegemiddel().hentNavn()+". Ingen gjenvaerende reit.");
      tilbake();
    }
  }

  //Skriver ut statistikk for vanedannende og narkotiske legemidler, samt leger og pasienter med narkotiske resepter
  public void skrivUtStatistikk(){
    int totVane = 0;
    int totNark = 0;
    HashMap<String,Integer> legerMedNarkotisk = new HashMap();
    HashMap<String,Integer> pasienterMedNarkotisk = new HashMap();

    for(Resept resept: resepter){
      if(resept.hentLegemiddel() instanceof Vanedannende) totVane++;
      else if(resept.hentLegemiddel() instanceof Narkotisk) totNark++;
    }

    System.out.println("\nTotalt antall utskrevene resepter med vanedannende legemidler: "+totVane);
    System.out.println("Totalt antall utskrevene resepter med narkotiske legemidler: "+totNark);
    System.out.println();

    for(Lege lege: leger){
      Lenkeliste<Resept> r = lege.hentResepter();
      int antall = 0;
      for(Resept res: r){
        if(res.hentLegemiddel() instanceof Narkotisk){
          antall++;
        }
      }
      if(antall>0){
        legerMedNarkotisk.put(lege.hentNavn(),antall);
      }
    }

    for(Pasient pasient: pasienter){
      Lenkeliste<Resept> r = pasient.hentResepter();
      int antall = 0;
      for(Resept res: r){
        if(res.hentLegemiddel() instanceof Narkotisk){
          antall++;
        }
      }
      if(antall>0){
        pasienterMedNarkotisk.put(pasient.hentNavn(),antall);
      }
    }

    System.out.println("Leger som har skrevet ut resepter på narkotiske legemidler:");
    legerMedNarkotisk.forEach((k,v) -> System.out.println("Lege: "+ k + ", antall utskrevne narkotiske resepter: " + v));
    System.out.println();
    System.out.println("Pasienter som har resepter på narkotiske legemidler:");
    pasienterMedNarkotisk.forEach((k,v) -> System.out.println("Pasient: "+ k + ", antall utskrevne narkotiske resepter: " + v));

    tilbake();
  }

  //Skriver ut nåverende system over til en fil i samme format som innfilen
  public void skrivTilFil(){
    inp = new Scanner(System.in);
    System.out.println("\nHva skal filnavnet vaere?");
    System.out.print("> ");
    String filnavn = inp.nextLine();
    FileWriter fil;
    try{
      fil = new FileWriter(filnavn);

      fil.write("# Pasienter (navn,fnr)\n");
      for(Pasient pas:pasienter){
        String navn = pas.hentNavn();
        String fnr = pas.hentFodselsnummer();
        fil.write(navn+","+fnr+"\n");
      }
      fil.write("# Legemidler (navn,type,pris,virkestoff,[styrke])\n");
      for(Legemiddel lm:legemidler){
        String navn = lm.hentNavn();
        String type = lm.hentType();
        Double pris = lm.hentPris();
        Double mg = lm.hentVirkestoff();
        int styrke = lm.hentStyrke();
        if(!(lm instanceof Vanlig)){
          fil.write(navn+","+type+","+pris+","+mg+","+styrke+"\n");
        }
        else{
          fil.write(navn+","+type+","+pris+","+mg+"\n");
        }
      }
      fil.write("# Leger (navn,kontrollId / 0 hvis vanlig lege)\n");
      for(Lege lege:leger){
        String navn = lege.hentNavn();
        int kontrollId = 0;
        if(lege instanceof Spesialist){
          Spesialist spes = (Spesialist) lege;
          kontrollId =spes.hentKontrollID();
        }
        fil.write(navn+","+kontrollId+"\n");
      }
      fil.write("# Resepter (legemiddelNummer, legeNavn, pasientId, type, [reit])\n");
      for(Resept res:resepter){
        int lmNr = res.hentLegemiddel().hentId();
        String lNavn = res.hentLege().hentNavn();
        int pId = res.hentPasient().hentId();
        String type = "";
        int reit = 0;
        if(res instanceof HvitResept){
          type = "hvit";
          reit = res.hentReit();
        }
        else if(res instanceof BlaaResept){
          type = "blaa";
          reit = res.hentReit();
        }
        else if(res instanceof Militaerresept){
          type = "militaer";
          reit = res.hentReit();
        }
        else{
          type = "p";
          reit = 3;
        }
        fil.write(lmNr+","+lNavn+","+pId+","+type+","+reit+"\n");
      }
      fil.close();
    }
    catch(IOException e){
      System.out.println("Noe gikk feil");
      e.printStackTrace();
    }
    System.out.println("Filen ble opprettet.");
    tilbake();
  }

  //Metode som venter på at brukeren gir input før den avsluttes
  private void tilbake(){
    inp = new Scanner(System.in);
    System.out.print("\nTrykk enter for aa gaa tilbake til hovedmenyen.");
    inp.nextLine();
  }
}
