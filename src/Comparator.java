import java.io.*;

/***
 *  Сравнение текстов в двух файлах
 */

public class Comparator {

    public static void main(String[] args) {

        try {

            File input1 = new File( "Data/Input1.txt" );
            File input2 = new File( "Data/Input2.txt" );
            BufferedReader reader = new BufferedReader( new FileReader( input1 ) );
            String text1 = readText( reader );
            reader = new BufferedReader( new FileReader( input2 ) );
            String text2 = readText( reader );
            Sequence sequence = findMSS( text1, text2 );
            System.out.println( sequence.sqnc1 );
            System.out.println( sequence.sqnc2 );
        } catch ( Exception e ) {

            e.printStackTrace();
        }
    }

    private static String readText( BufferedReader reader ) throws IOException {

        StringBuilder result = new StringBuilder();
        String temp;
        while ( ( temp = reader.readLine() ) != null ) {

            result.append( temp );
        }
        return String.valueOf( result );
    }

    private static String longestSS( String a, String b ) {

        if( a == null || b == null || a.length() == 0 || b.length() == 0 ) {

            return "";
        }

        if( a.equals( b ) ) {

            return a;
        }

        int[][] matrix = new int [ a.length() ][];
        int maxLength = 0;
        int maxI = 0;
        for (int i = 0; i < matrix.length; i++) {

            matrix[ i ] = new int[ b.length() ];
            for (int j = 0; j < matrix[ i ].length; j++) {

                if ( a.charAt( i ) == b.charAt( j ) ) {

                    if( i != 0 && j != 0 ) {

                        matrix[ i ][ j ] = matrix[ i - 1 ][ j - 1 ] + 1;
                    } else {

                        matrix[ i ][ j ] = 1;
                    }

                    if( matrix[ i ][ j ] > maxLength ) {

                        maxLength = matrix[ i ][ j ];
                        maxI = i;
                    }
                }
            }
        }
        return a.substring( maxI - maxLength + 1, maxI + 1 );
    }


    private static Sequence findMSS(String a, String b ) {

        int m = a.length();
        int n = b.length();
        int[][] L = new int[ m + 1 ][ n + 1 ];
        for (int i = 0; i <= m; i++) {

            for (int j = 0; j <= n ; j++) {

                if( i == 0 || j == 0 ) {

                    L[ i ][ j ] = 0;
                } else if( a.charAt( i - 1 ) == b.charAt( j - 1 ) ) {

                    L[ i ][ j ] = L[ i - 1 ][ j - 1 ] + 1;
                } else {

                    L[ i ][ j ] = Math.max( L[ i - 1 ][ j ], L[ i ][ j - 1 ] );
                }
            }
        }

        int index = L[ m ][ n ];
        char[] lcs = new char[ index + 1 ];
        char[] aDiff = new char[ m - index ];
        int aDiffLength = aDiff.length;
        lcs[ index ] = '\0';
        int i = m, j = n;
        while( i > 0 && j > 0 ) {

            if( a.charAt( i - 1 ) == b.charAt( j - 1 ) ) {

                lcs[ index - 1 ] = a.charAt( i - 1 );
                i--;
                j--;
                index--;
            } else if(  L[ i - 1 ][ j ] > L[ i ][ j - 1 ]  ) {

                aDiff[ aDiffLength - 1 ] = a.charAt( i - 1 );
                aDiffLength--;
                i--;
            } else  {

                j--;
            }
        }
        if ( i != 0 ) {

            for (int k = 0; k < i; k++) {

                aDiff[ aDiffLength - 1 ] = a.charAt( i - 1 );
                aDiffLength--;
            }
        }
        Sequence sequence = new Sequence();
        sequence.sqnc1 = String.valueOf( lcs, 0, lcs.length - 1 );
        sequence.sqnc2 = String.valueOf( aDiff );
        return sequence;
    }

    private static class Sequence {

        private String sqnc1;
        private String sqnc2;
    }
}
