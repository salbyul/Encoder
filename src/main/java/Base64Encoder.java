public class Base64Encoder implements Encoder{

    private static char[] arr = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    @Override
    public String encode(String value) {
        StringBuilder sb = new StringBuilder();
        int rest;

        // 2진수로 변환
        for (int i = 0; i < value.length(); i++) {
            sb.append(String.format("%8s", Integer.toBinaryString(value.charAt(i))).replaceAll(" ", "0"));
        }

        // 24비트씩 가져와서 6비트 블록으로 그룹화하는 메서드
        String[] sixBits = transferSixBit(sb.toString(), value.length());
        rest = 6 - sixBits[sixBits.length - 1].length();
        sb.setLength(0);

        for (int i = 0; i < rest; i++) {
            sixBits[sixBits.length - 1] = sixBits[sixBits.length - 1] + "0";
        }

        // 블록을 십진수로 변환하는 메서드
        int[] digits = toDigits(sixBits);

        // Base64 테이블 보고 문자로 변환
        for (int digit : digits) {
            sb.append(arr[digit]);
        }

        while (rest != 0) {
            sb.append("=");
            rest -= 2;
        }

        return sb.toString();
    }

    private int[] toDigits(String[] sixBits) {
        int[] digits = new int[sixBits.length];

        for (int i = 0; i < digits.length; i++) {
            digits[i] = Integer.parseInt(sixBits[i], 2);
        }
        return digits;
    }

    private String[] transferSixBit(String s, int len) {

        String[] result = new String[len % 3 == 0 ? len / 3 * 4 : ((len / 3 * 4) + (len % 3 == 1 ? 2 : 3))];
        int startIndex = 0;
        int endIndex = 6;

        for (int i = 0; i < result.length - 1; i++) {
            result[i] = s.substring(startIndex, endIndex);
            startIndex = endIndex;
            endIndex += 6;
        }
        result[result.length - 1] = s.substring(startIndex);

        return result;
    }
}