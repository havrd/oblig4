class Spesialist extends Lege implements Godkjenningsfritak{
  protected int kontrollId;

  //Konstruktor
  public Spesialist(String nvn, int kontroll){
    super(nvn);
    kontrollId = kontroll;
  }

  //Henter kontroll-ID
  public int hentKontrollID(){
    return kontrollId;
  }
}
