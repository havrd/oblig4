class Lege implements Comparable<Lege>{
  protected String navn;
  protected Lenkeliste<Resept> utskrevedeResepter;

  //Konstruktor
  public Lege(String nvn){
    if(nvn == null){
      throw new NullPointerException();
    }
    navn = nvn;
    utskrevedeResepter = new Lenkeliste<Resept>();
  }

  //Henter navnet til legen
  public String hentNavn(){
    return navn;
  }

  public Lenkeliste hentResepter(){
    return utskrevedeResepter;
  }

  public HvitResept skrivHvitResept(Legemiddel legemiddel, Pasient pasientid, int reit) throws UlovligUtskrift{
    if(legemiddel instanceof Narkotisk && !(this instanceof Spesialist)){
      throw new UlovligUtskrift(this, legemiddel);
    }
    else{
      HvitResept res = new HvitResept(legemiddel, this, pasientid, reit);
      utskrevedeResepter.leggTil(res);
      pasientid.leggTilResept(res);
      return res;
    }
  }

  public Militaerresept skrivMilitaerResept(Legemiddel legemiddel, Pasient pasientid, int reit) throws UlovligUtskrift{
    if(legemiddel instanceof Narkotisk && !(this instanceof Spesialist)){
      throw new UlovligUtskrift(this, legemiddel);
    }
    else{
      Militaerresept res = new Militaerresept(legemiddel, this, pasientid, reit);
      utskrevedeResepter.leggTil(res);
      pasientid.leggTilResept(res);
      return res;
    }
  }

  public Presept skrivPResept(Legemiddel legemiddel, Pasient pasientid) throws UlovligUtskrift{
    if(legemiddel instanceof Narkotisk && !(this instanceof Spesialist)){
      throw new UlovligUtskrift(this, legemiddel);
    }
    else{
      Presept res = new Presept(legemiddel, this, pasientid);
      utskrevedeResepter.leggTil(res);
      pasientid.leggTilResept(res);
      return res;
    }
  }

  public BlaaResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasientid, int reit) throws UlovligUtskrift{
    if(legemiddel instanceof Narkotisk && !(this instanceof Spesialist)){
      throw new UlovligUtskrift(this, legemiddel);
    }
    else{
      BlaaResept res = new BlaaResept(legemiddel, this, pasientid, reit);
      utskrevedeResepter.leggTil(res);
      pasientid.leggTilResept(res);
      return res;
    }
  }

  public int compareTo(Lege lege){
    return navn.compareTo(lege.hentNavn());
  }

  public String toString(){
    return navn;
  }
}
