class TestSystem{
  public static void main(String[] args) throws UlovligUtskrift{
    Lege lege = new Lege("Per");
    Spesialist spes = new Spesialist("Andreas", 12345);
    Narkotisk nark = new Narkotisk("Benzo", 12.2, 99.9, 9);
    Vanedannende vane = new Vanedannende("THC", 0.4, 200.0, 6);
    Vanlig vanlig = new Vanlig("Ibux", 200.0, 49.9);
    Pasient pas = new Pasient("Gorl", "12109520304");
    spes.skrivHvitResept(nark, pas, 3);
    //lege.skrivHvitResept(nark, pas, 3);
    spes.skrivHvitResept(vane, pas, 3);
    spes.skrivHvitResept(vanlig, pas, 3);
    spes.skrivBlaaResept(vanlig, pas, 333);

    Lenkeliste<Resept> listeSpes = spes.hentResepter();
    //System.out.println(listeSpes.hent(0) instanceof Resept);

    for(Resept res:listeSpes){
      System.out.println(res);
    }

    SortertLenkeliste<Lege> listeLeger = new SortertLenkeliste();
    listeLeger.leggTil(lege);
    listeLeger.leggTil(spes);

    for(Lege l:listeLeger){
      System.out.println(l);
    }
  }
}
