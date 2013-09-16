package interviewquestions;

/**
 * Take the ordinal number of a column in a spreadsheet and return the corresponding
 * label.
 *
 * Ex:
 *   1 -> A
 *   2 -> B
 *   26 -> Z
 *   27 -> AA
 */
public class SpreadSheet {
  private final static char[] LETTERS = new char[] {
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
    'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
    'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
  };

  public static String ordinalToLabel(int ordinal) {
    StringBuilder builder = new StringBuilder();

    while (ordinal > 0) {
      ordinal--;
      builder.append(LETTERS[ordinal % LETTERS.length]);
      ordinal /= LETTERS.length;
    }

    return builder.reverse().toString();
  }

  public static String ordinalToLabelRecursive(int ordinal) {
    if (ordinal <= 0) return "";

    ordinal--;
    char ch = LETTERS[ordinal % LETTERS.length];
    return ordinalToLabel(ordinal / LETTERS.length) + Character.toString(ch);
  }

  public static void main(String[] args) {
    test(ordinalToLabel(1).compareTo("A") == 0);
    test(ordinalToLabel(2).compareTo("B") == 0);
    test(ordinalToLabel(3).compareTo("C") == 0);
    test(ordinalToLabel(26).compareTo("Z") == 0);
    test(ordinalToLabel(27).compareTo("AA") == 0);
    test(ordinalToLabel(52).compareTo("AZ") == 0);
    test(ordinalToLabel(442).compareTo("PZ") == 0);
    test(ordinalToLabel(26 * 7).compareTo("FZ") == 0);
    test(ordinalToLabel(26 * 27).compareTo("ZZ") == 0);
    test(ordinalToLabel(26 * 27 + 1).compareTo("AAA") == 0);

    test(ordinalToLabelRecursive(1).compareTo("A") == 0);
    test(ordinalToLabelRecursive(2).compareTo("B") == 0);
    test(ordinalToLabelRecursive(3).compareTo("C") == 0);
    test(ordinalToLabelRecursive(26).compareTo("Z") == 0);
    test(ordinalToLabelRecursive(27).compareTo("AA") == 0);
    test(ordinalToLabelRecursive(52).compareTo("AZ") == 0);
    test(ordinalToLabelRecursive(442).compareTo("PZ") == 0);
    test(ordinalToLabelRecursive(26 * 7).compareTo("FZ") == 0);
    test(ordinalToLabelRecursive(26 * 27).compareTo("ZZ") == 0);
    test(ordinalToLabelRecursive(26 * 27 + 1).compareTo("AAA") == 0);
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}


/*
    Reference table:

     1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
1    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z   26
2   AA AB AC AD AE AF AG AH AI AJ AK AL AM AN AO AP AQ AR AS AT AU AV AW AX AY AZ   52
3   BA BB BC BD BE BF BG BH BI BJ BK BL BM BN BO BP BQ BR BS BT BU BV BW BX BY BZ   78
4   CA CB CC CD CE CF CG CH CI CJ CK CL CM CN CO CP CQ CR CS CT CU CV CW CX CY CZ  104
5   DA DB DC DD DE DF DG DH DI DJ DK DL DM DN DO DP DQ DR DS DT DU DV DW DX DY DZ  130
6   EA EB EC ED EE EF EG EH EI EJ EK EL EM EN EO EP EQ ER ES ET EU EV EW EX EY EZ  156
7   FA FB FC FD FE FF FG FH FI FJ FK FL FM FN FO FP FQ FR FS FT FU FV FW FX FY FZ  182
8   GA GB GC GD GE GF GG GH GI GJ GK GL GM GN GO GP GQ GR GS GT GU GV GW GX GY GZ  208
9   HA HB HC HD HE HF HG HH HI HJ HK HL HM HN HO HP HQ HR HS HT HU HV HW HX HY HZ  234
10  IA IB IC ID IE IF IG IH II IJ IK IL IM IN IO IP IQ IR IS IT IU IV IW IX IY IZ  260
11  JA JB JC JD JE JF JG JH JI JJ JK JL JM JN JO JP JQ JR JS JT JU JV JW JX JY JZ  286
12  KA KB KC KD KE KF KG KH KI KJ KK KL KM KN KO KP KQ KR KS KT KU KV KW KX KY KZ  312
13  LA LB LC LD LE LF LG LH LI LJ LK LL LM LN LO LP LQ LR LS LT LU LV LW LX LY LZ  338
14  MA MB MC MD ME MF MG MH MI MJ MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ  364
15  NA NB NC ND NE NF NG NH NI NJ NK NL NM NN NO NP NQ NR NS NT NU NV NW NX NY NZ  390
16  OA OB OC OD OE OF OG OH OI OJ OK OL OM ON OO OP OQ OR OS OT OU OV OW OX OY OZ  416
17  PA PB PC PD PE PF PG PH PI PJ PK PL PM PN PO PP PQ PR PS PT PU PV PW PX PY PZ  442
18  QA QB QC QD QE QF QG QH QI QJ QK QL QM QN QO QP QQ QR QS QT QU QV QW QX QY QZ  468
19  RA RB RC RD RE RF RG RH RI RJ RK RL RM RN RO RP RQ RR RS RT RU RV RW RX RY RZ  494
20  SA SB SC SD SE SF SG SH SI SJ SK SL SM SN SO SP SQ SR SS ST SU SV SW SX SY SZ  520
21  TA TB TC TD TE TF TG TH TI TJ TK TL TM TN TO TP TQ TR TS TT TU TV TW TX TY TZ  546
22  UA UB UC UD UE UF UG UH UI UJ UK UL UM UN UO UP UQ UR US UT UU UV UW UX UY UZ  572
23  VA VB VC VD VE VF VG VH VI VJ VK VL VM VN VO VP VQ VR VS VT VU VV VW VX VY VZ  598
24  WA WB WC WD WE WF WG WH WI WJ WK WL WM WN WO WP WQ WR WS WT WU WV WW WX WY WZ  624
25  XA XB XC XD XE XF XG XH XI XJ XK XL XM XN XO XP XQ XR XS XT XU XV XW XX XY XZ  650
26  YA YB YC YD YE YF YG YH YI YJ YK YL YM YN YO YP YQ YR YS YT YU YV YW YX YY YZ  676
27  ZA ZB ZC ZD ZE ZF ZG ZH ZI ZJ ZK ZL ZM ZN ZO ZP ZQ ZR ZS ZT ZU ZV ZW ZX ZY ZZ  702
28  AAA
*/