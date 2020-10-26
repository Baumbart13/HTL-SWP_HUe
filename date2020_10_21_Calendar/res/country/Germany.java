package date2020_10_21_Calendar.res.country;

public enum Germany {
    BadenWuerttemberg,
    Bayern,
    Berlin,
    Brandenburg,
    Bremen,
    Hamburg,
    Hessen,
    MecklenburgVorpommern,
    Niedersachsen,
    NordrheinWestfalen,
    RheinlandPfalz,
    Saarland,
    Sachsen,
    SachsenAnhalt,
    SchleswigHolstein,
    Thueringen,
    National;

    @Override
    public String toString(){
        switch(this){
            case BadenWuerttemberg: return "BW";
            case Bayern: return "BY";
            case Berlin: return "BE";
            case Brandenburg: return "BB";
            case Bremen: return "HB";
            case Hamburg: return "HH";
            case Hessen: return "HE";
            case MecklenburgVorpommern: return "MV";
            case Niedersachsen: return "NI";
            case NordrheinWestfalen: return "NW";
            case RheinlandPfalz: return "RP";
            case Saarland: return "SL";
            case Sachsen: return "SN";
            case SachsenAnhalt: return "ST";
            case SchleswigHolstein: return "SH";
            case Thueringen: return "TH";
        }
        return "NATIONAL";
    }

    public static String[] getAll(){
        return new String[]{BadenWuerttemberg.toString(), Bayern.toString(), Berlin.toString(), Brandenburg.toString(),
                Bremen.toString(), Hamburg.toString(), Hessen.toString(), MecklenburgVorpommern.toString(),
                Niedersachsen.toString(), NordrheinWestfalen.toString(), RheinlandPfalz.toString(), Saarland.toString(),
                Sachsen.toString(), SachsenAnhalt.toString(), SchleswigHolstein.toString(), Thueringen.toString()};
    }
}
