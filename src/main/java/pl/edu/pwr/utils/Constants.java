package pl.edu.pwr.utils;

public class Constants {

    public static final String[] SIDE_HEADERS_COMPANY = {
    "COMPANY",
    "NAME",
    "ADDRESS",
    "CITY",
    "REGON",
    "NIP",
    "REPRESENTATIVE",
            "ROLE"
    };

    public static final String[] SIDE_HEADERS_NONCOMPANY = {
            "COMPANY",
            "NAME",
            "ADDRESS",
            "CITY",
            "PESEL",
            "ID",
            "ROLE"
    };

    public static final String[] RENT = {
            "czynsz"
    };

    public static final String[] ROOMS = {
            "pokój",
            "pokoje"
    };

    public static final String[] PENALTY = {
            "kara"
    };

    public static final String[] SALARY = {
            "wynagrodzenie"
    };

    public static final String[] LEASE_AGREEMENT_HEADERS = {
            "LEASE_SUBJECT",
            "NUMBER_OF_ROOMS",
            "LEASE_SUBJECT_ADDRESS",
            "CONTRACT_PERIOD",
            "DEFINED_PERIOD",
            "RENT",
            "DENUNCIATION_TIME",
            "DEPOSIT"
    };
    public static final String[] CONTRACT_OF_MANDATE_DETAILS = {
            "DESCRIPTION",
            "START_DATE",
            "END_DATE",
            "SALARY"
    };

    public static final String[] CONTRACT_WORK_DETAILS = {
            "DESCRIPTION",
            "DEADLINE",
            "START_DATE",
            "SALARY",
            "DELAY_PENALTY"
    };
    public static final String[] CONTRACT_WORK_DESC = {
            "zamawiający",
            "zamawiać",
            "przyjmujący",
            "zobowiązywać",
            "przyjmować"
    };

    //liczby porządkowe zaczynające część zdania
    public static String[] ORDINAL_NUMBERS = {
            "1.",
            "2.",
            "3.",
            "4.",
            "5.",
            "6.",
            "7.",
            "8.",
            "9."
    };

    public static String[] PLACE_DEFINITION = {
            "lokal"
    };
    //skróty, które nie mogą być na końcu zdania, ale często mają po sobie dużą literę
    public static String[] SHORTCUTS_NOT_ENDING_SENTENCE = {
            "1.",
            "2.",
            "3.",
            "4.",
            "5.",
            "6.",
            "7.",
            "8.",
            "9.",
            "al.",
            "ang.",
            "bł.",
            "belg.",
            "dent.",
            "dep.",
            "doc.",
            "dr n. med.",
            "dyr.",
            "dz.",
            "farm.",
            "franc.",
            "fr.",
            "gen.",
            "g.",
            "godz.",
            "hab.",
            "inż.",
            "kard.",
            "ks.",
            "książ.",
            "lek.",
            "lek. dent.",
            "lek. stom.",
            "lek. wet.",
            "lic.",
            "łac.",
            "med.",
            "nadzw.",
            "ndzw.",
            "nzw.",
            "nz.",
            "np.",
            "o.",
            "oo.",
            "p.o.",
            "pl.",
            "pt.",
            "prof.",
            "stom.",
            "św.",
            "śp.",
            "tj.",
            "ukr.",
            "ul.",
            "zob."
    };

    //Jeżeli ten skrót występuje i nie ma po nim dużej litery to nie ma końca zdania
    public static String[] SHORTCUTS = {
            "a.c.",
            "a.t.",
            "a.u.c.",
            "abs.",
            "absolw.",
            "a.i.",
            "a.m.",
            "acc.",
            "a.s.c.",
            "a.",
            "arch.",
            "przest.",
            "art.",
            "b.",
            "b.d.",
            "blp.",
            "b.m.",
            "b.p.",
            "niesygn.",
            "b.r.",
            "b.u.",
            "bm.",
            "br.",
            "błp.",
            "bot.",
            "cdn.",
            "col.",
            "cykl.",
            "cyt.",
            "cz.",
            "czyt.",
            "d.",
            "dgn.",
            "diag.",
            "rozp.",
            "ds.",
            "dol.",
            "dosł.",
            "eeg.",
            "ekg.",
            "emg.",
            "e.i.",
            "e.a.",
            "etc.",
            "fant.",
            "flam.",
            "fot.",
            "hol.",
            "nid.",
            "hr.",
            "i in.",
            "itd.",
            "itp.",
            "i wsp.",
            "ib.",
            "ibid.",
            "inst.",
            "iron.",
            "jw.",
            "jm.",
            "jęz.",
            "jun.",
            "kol.",
            "k.",
            "kop.",
            "kor.",
            "kr.",
            "l.",
            "l.dz.",
            "ldz.",
            "lm.",
            "lmn.",
            "lp.",
            "lb.",
            "w.",
            "v.",
            "l.",
            "lit.",
            "l.c.",
            "l.d.",
            "log.",
            "łow.",
            "mc.",
            "m.b.",
            "mkw.",
            "m.",
            "mies.",
            "m.in.",
            "muz.",
            "n.",
            "n.p.m.",
            "nast.",
            "n.e.",
            "nt.",
            "nlb.",
            "nw.",
            "nb.",
            "N.T.",
            "ob.",
            "o.o.",
            "op. cit.",
            "os.",
            "p.",
            "pp.",
            "pes.",
            "p. Chr.",
            "p.C.",
            "poch.",
            "pn.",
            "poj.",
            "płd.",
            "por.",
            "p.m.",
            "pow.b.",
            "pb.",
            "pow.",
            "płn.",
            "przec.",
            "prz. Chr.",
            "a.C.",
            "p.n.e.",
            "ps.",
            "psycht.",
            "psych.",
            "psychl.",
            "q.",
            "qu.",
            "rtg.",
            "r.",
            "rż.",
            "ros.",
            "rozdz.",
            "rozp.",
            "ryc.",
            "rys.",
            "sam.",
            "sen.",
            "sek.",
            "s.",
            "sp.",
            "str.",
            "ss.",
            "sygn.",
            "tab.",
            "t.",
            "ub. m.",
            "ub. r.",
            "v.",
            "vs.",
            "v.v.",
            "ob.",
            "p.",
            "w.d.",
            "w.g.",
            "wł.",
            "wsch.",
            "wsp.",
            "wydz.",
            "wyj.",
            "wym.",
            "ww.",
            "z o.o.",
            "zach.",
            "za gr.",
            "zagr.",
            "zł.",
            "zool.",
            "żart.",
            "żeń.",
            "źr."
    };

    public static String[] CITIES = {
            "Bolesławiec",
            "Wałbrzych",
            "Warszawa",
            "Kraków",
            "Łódź",
            "Wrocław",
            "Poznań",
            "Gdańsk",
            "Szczecin",
            "Bydgoszcz",
            "Lublin",
            "Katowice"
    };

    public static String[] FIRSTNAMES = {
            "Kazimierz",
            "Anna",
            "Piotr",
            "Maria",
            "Krzysztof",
            "Katarzyna",
            "Andrzej",
            "Małgorzata",
            "Jan",
            "Agnieszka",
            "Stanisław",
            "Barbara",
            "Tomasz",
            "Krystyna",
            "Paweł",
            "Ewa",
            "Marcin",
            "Elżbieta",
            "Michał",
            "Zofia",
            "Marek",
            "Teresa",
            "Grzegorz",
            "Magdalena",
            "Józef",
            "Joanna",
            "Łukasz",
            "Janina",
            "Adam",
            "Monika",
            "Zbigniew",
            "Danuta",
            "Jerzy",
            "Jadwiga",
            "Tadeusz",
            "Aleksandra",
            "Mateusz",
            "Halina",
            "Dariusz",
            "Irena",
            "Mariusz",
            "Artur"
    };

    public static String[] ADDRESS = {
        "ul",
        "al",
        "pl",
        "ulica",
        "aleja",
        "plac"
    };

    public static String[] PLACE = {
            "lokal",
            "mieszkanie",
            "położyć"
    };

    public static String[] CONTRACT_OF_MANDATE_DESC = {
            "zleceniodawca",
            "zleceniobiorca",
            "zlecać",
            "przyjmować"
    };

    public static String[] CONTRACT = { "umowa", "umowy" };

    public static String EMPLOYMENT_CONTRACT = "praca";

    public static String CONTRACT_OF_MANDATE = "zlecenie";

    public static String CONTRACT_WORK = "dzieło";

    public static String[] LEASE_AGREEMENT = {
            "najem",
            "dzierżawa",
            "leasing"
    };

    public static String[] IGNORE_NAME = {
            "pan",
            "pani",
            "pracodawca",
            "pracownik",
            "najemca",
            "wynajmujący",
            "wynajmująca",
            "zamawiający",
            "wykonawca",
            "zleceniodawca",
            "zleceniobiorca",
            "zawarty",
            "zawarta"
    };

    public static String SPECIAL_CHARACTERS = "-—–";

    public static String INDEFINITE_PERIOD = "nieokreślony";

    public static String DEFINITE_PERIOD = "określony";

    public static String[] DEFINITE_PERIOD_LIST = { "określony", "oznaczony" };

    public static String[] INDEFINITE_PERIOD_LIST = { "nieokreślony", "nieoznaczony"};

    public static String PROBATIONARY_PERIOD = "próbny";

    public static String POSITION = "stanowisko";

    public static String[] WORKING_PLACE = {"wykonywać", "miejsce", "siedziba"};

    public static String[] START_DATE = {"począć", "rozpoczęcie", "rozpocząć", "zobowiązywać"};

    public static String BRUTTO = "brutto";

    public static String[] EMPLOYMENT_CONTRACT_HEADERS = {
            "CONTRACT_PERIOD",
            "DEFINITE_PERIOD",
            "HOURLY_DIMENSION",
            "PART_TIME_DIMENSION",
            "POSITION",
            "WORKING_PLACE",
            "START_DATE",
            "WORKING_HOURS",
            "SALARY"
    };

    public static String[] TERMINATION_CONTRACT_HEADERS = {
            "PREVIOUS_CONTRACT_DATE",
            "PREVIOUS_CONTRACT_PLACE",
            "CONTRACT_EFFECT_DATE",
            "REQUESTING_PARTY"
    };

    public static String[] CONTRACT_HEADERS = {
            "PLACE_DATE",
            "FIRST_SIDE",
            "SECOND_SIDE",

    };

    public static String[] CONTRACT_GENERAL = {
            "TYPE",
            "SIDES",
            "CONCLUSION_PLACE",
            "CONCLUSION_DATE",
    };

    public static String[] DENUNCIATION = {
            "wypowiedzenie",
            "rozwiązanie",
            "zerwanie"
    };

    public static String[] DEPOSIT = {
      "depozyt",
      "kaucja"
    };

    public static String[] PERIOD = {
            "dniowy",
            "tygodniowy",
            "miesięczny",
    };
}
