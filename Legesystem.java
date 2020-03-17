import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;


class Legesystem{

  protected Lenkeliste<Pasient> pasienter = new Lenkeliste();
  protected Lenkeliste<Legemiddel> legemidler = new Lenkeliste();
  protected Lenkeliste<Lege> leger = new SortertLenkeliste();
  protected Lenkeliste<Resept> resepter = new Lenkeliste();
  private Scanner inp;
  private int input;

  public static void main(String[] args) throws UlovligUtskrift{
    Legesystem system = new Legesystem();
    system.lesFil("myeInndata.txt");
    system.hovedmeny();
  }


  public void lesFil(String filnavn) throws UlovligUtskrift{
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

    while(inp.hasNextLine()){
      String linje = inp.nextLine();
      if(linje.startsWith("#")){
        type++;
      }

      else{
        String[] biter = linje.trim().split(",");

        if(type==1){
          Pasient pas = new Pasient(biter[0], biter[1]);
          pasienter.leggTil(pas);
        }

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

        else if(type==4){
          Legemiddel lm = null;
          Pasient p = null;
          Lege l = null;
          String typeResept = biter[3];
          Resept r = null;

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

          if (typeResept.equals("hvit")){
            try{
              r = l.skrivHvitResept(lm, p, Integer.parseInt(biter[4]));
              resepter.leggTil(r);
            }
            catch(NullPointerException | UlovligUtskrift e){
              feilResept++;
            }
          }

          else if (typeResept.equals("blaa")){
            try{
              r = l.skrivBlaaResept(lm, p, Integer.parseInt(biter[4]));
              resepter.leggTil(r);
            }
            catch(NullPointerException | UlovligUtskrift e){
              feilResept++;
            }
          }

          else if (typeResept.equals("militaer")){
            try{
              r = l.skrivMilitaerResept(lm, p, Integer.parseInt(biter[4]));
              resepter.leggTil(r);
            }
            catch(NullPointerException | UlovligUtskrift e){
              feilResept++;
            }
          }

          else if (typeResept.equals("p")){
            try{
              r = l.skrivPResept(lm, p);
              resepter.leggTil(r);
            }
            catch(NullPointerException | UlovligUtskrift e){
              feilResept++;
            }
          }
        }
      }
    }

    System.out.println();
    System.out.println(pasienter.stoerrelse()+" pasienter ble lagt inn i systemet.");
    System.out.println(legemidler.stoerrelse()+" legemidler ble lagt inn i systemet.");
    System.out.println(leger.stoerrelse()+" leger ble lagt inn i systemet.");
    System.out.println(resepter.stoerrelse()+" resepter ble lagt inn i systemet.");
    System.out.println(feilLegemiddel+" legemidler ble ikke opprettet på grunn av feil format.");
    System.out.println(feilResept+" resepter ble ikke opprettet på grunn av feil format eller ulovlig utskrift.\n");
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

  public void hovedmeny(){
    input = 9;

    while(input!=0){
      skrivUtHovedmeny();
      System.out.print("Velg handling: >");
      inp = new Scanner(System.in);
      try{
        input = inp.nextInt();
      }
      catch(InputMismatchException e){
        System.out.println("Skriv et tall for å velge handling.");
        input = 9;
      }
      if(input == 1){
        skrivUtOversikt();
        input = 9;
      }
      else if(input == 2){
        leggTilElement();
        input = 9;
      }
      else if(input == 3){
        brukResept();
        input = 9;
      }
      else if(input == 4){
        skrivUtStatistikk();
        input = 9;
      }
      else if(input == 5){
        skrivTilFil();
        input = 9;
      }
    }
  }

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
    return;
  }

  public void leggTilElement(){
    return;
  }

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
      input = inp.nextInt();
    }
    catch(InputMismatchException e){
      System.out.println("Skriv et tall for å velge pasient. Går tilbake til hovedmenyen.");
      return;
    }

    if(input > pasienter.stoerrelse()-1 || input < 0){
      System.out.println("Ikke gjenkjent handling, går tilbake til hovedmeny.");
      return;
    }
    valgtPasient = pasienter.hent(input);
    reseptListe = valgtPasient.hentResepter();

    System.out.println("Valgt pasient: "+valgtPasient);
    System.out.println("\nHvilken resept vil du bruke? ");
    for(int i =0; i<reseptListe.stoerrelse();i++){
      System.out.println(i+": "+reseptListe.hent(i).hentLegemiddel().hentNavn() + " ("+reseptListe.hent(i).hentReit()+" reit)");
    }
    System.out.print("Velg resept: >");
    try{
      input = inp.nextInt();
    }
    catch(InputMismatchException e){
      System.out.println("Skriv et tall for å velge resept. Går tilbake til hovedmenyen.");
      return;
    }

    if(input > valgtPasient.hentResepter().stoerrelse()-1 || input < 0){
      System.out.println("Ikke gjenkjent handling, går tilbake til hovedmeny.");
      return;
    }

    if(reseptListe.hent(input).bruk()){
      System.out.println("Brukte resept på "+reseptListe.hent(input).hentLegemiddel().hentNavn()+". Antall gjenvaerende reit: "+reseptListe.hent(input).hentReit());
    }
    else{
      System.out.println("Kunne ikke bruke resept på "+reseptListe.hent(input).hentLegemiddel().hentNavn()+". Ingen gjenvaerende reit.");
    }
    return;
  }

  public void skrivUtStatistikk(){
    return;
  }

  public void skrivTilFil(){
    return;
  }

}
