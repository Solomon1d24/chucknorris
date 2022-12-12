import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please input operation (encode/decode/exit):");
            String input = scanner.nextLine();
            switch (input) {
                case "encode":
                    encode();
                    break;
                case "decode":
                    try {
                        decode();
                    } catch (NoSuchElementException | NullPointerException exception) {
                        System.out.println("Encoded string is not valid.");
                    }
                    break;
                case "exit":
                    System.out.println("Bye!");
                    System.exit(0);
                default:
                    System.out.println("There is no " + "'" + input + "'" + " operation");
                    break;
            }
        }
    }


    private static void encode() {
        System.out.println("Input String: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        List<Character> characterList = new ArrayList<>();

        int maxIndex = input.length() - 1;
        for (int i = 0; i <= maxIndex; i++) {
            characterList.add(input.charAt(i));
        }
        List<Map<String, String>> result = null;
        List<String> binarys = Main.convertStringToBinary(characterList);
        System.out.println("Encoded string:");

        StringBuilder finalString = new StringBuilder();
        for (String binary : binarys) {
            finalString.append(binary);
        }

        result = convertBinaryToChuck(finalString.toString());
        for (Map<String, String> map : result) {
            String key = "";
            if (map.containsKey("00")) {
                System.out.print("00 ");
                key = "00";
            } else {
                System.out.print("0 ");
                key = "0";
            }
            String val = map.get(key);
            System.out.print(val + " ");
        }
        System.out.println();
    }


    private static void decode() throws RuntimeException {
        System.out.println("Input encoded string: ");
        Scanner scanner = new Scanner(System.in);
        List<Map<String, String>> inputs = new ArrayList<>();
        String input = scanner.nextLine();
        String pattern = "[0]*";
        Scanner scanner1 = new Scanner(input);
        while (scanner1.hasNext()) {
            Map<String, String> map = new HashMap<>();
            String key = scanner1.next(pattern);
            String val = scanner1.next(pattern);
            map.put(key, val);
            inputs.add(map);
        }
        String binary = convertChunkToBinary(inputs);
        if(binary.length()%7 !=0){
            throw new NullPointerException();
        }
        List<String> splitInputs = splitInput(binary);
        String result = binaryToString(splitInputs);
        System.out.println("Decoded string:");
        System.out.println(result);
    }


    private static List<String> convertStringToBinary(List<Character> characterList) {
        List<String> resultsList = new ArrayList<>();
        characterList.forEach(character -> {
            String binaryCharacter = String.format("%7s", Integer.toBinaryString(character)).replaceAll(" ", "0");
            resultsList.add(binaryCharacter);
        });
        return resultsList;
    }

    private static List<Map<String, String>> convertBinaryToChuck(String binary) {
        char[] characters = binary.toCharArray();
        int i = 0;
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> map;
        while (i < characters.length) {
            Character temp = characters[i];
            String label = "";
            int count = 1;
            i++;
            while (i < characters.length && temp.equals(characters[i])) {
                count++;
                i++;
            }
            map = new HashMap<>();
            if (temp == '0') {
                label = "00";
            } else {
                label = "0";
            }
            String val = "0".repeat(count);
            map.put(label, val);
            result.add(map);
        }
        return result;
    }

    private static String convertChunkToBinary(List<Map<String, String>> maps) {
        StringBuilder stringBuilder = new StringBuilder();
        maps.forEach(map -> {
            String temp;
            if (map.containsKey("0")) {
                temp = "1".repeat(map.get("0").length());
            } else {
                temp = "0".repeat(map.get("00").length());
            }
            stringBuilder.append(temp);
        });
        return stringBuilder.toString();
    }

    private static List<String> splitInput(String input) {
        List<String> results = new ArrayList<>();
        int index = 0;
        while (index < input.length()) {
            results.add(input.substring(index, Math.min(index + 7, input.length())));
            index += 7;
        }
        return results;
    }

    private static String binaryToString(List<String> inputs) {
        StringBuilder stringBuilder = new StringBuilder();
        inputs.forEach(input -> {
            int parseInt = Integer.parseInt(input, 2);
            char c = (char) parseInt;
            stringBuilder.append(c);
        });
        return stringBuilder.toString();
    }
}