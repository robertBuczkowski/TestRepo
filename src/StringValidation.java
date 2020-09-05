class StringValidation{
    static boolean validateString(String str) {
        str = str.toLowerCase();
        char[] charArray = str.toCharArray();
        for (char ch : charArray) {
            if (!((ch >= '0') && ch <= '1')) {
                return true;
            }
        }
        return false;
    }
}
