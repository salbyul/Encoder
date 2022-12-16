public interface Encoder extends Converter{

    String encode(String value);
    String decode(String value);
}
