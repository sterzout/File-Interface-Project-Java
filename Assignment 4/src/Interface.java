import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Interface {
    BSTDictionary dict = new BSTDictionary(new BinarySearchTree());

    //create a new dictionary for our interface class
    public Interface(String inputFile) throws DictionaryException {
        try (BufferedReader in = new BufferedReader(new FileReader(inputFile))) {
            // try file if sucessful we proceed
            String line = in.readLine();
            String lineAfter = in.readLine();
            // we read the label first as line then with lineAfter we want to create our data and get the type as well
            if (line == null || lineAfter == null) {
                throw new IOException("Null or end of file");
                // if either is null either there is an empty file or the file has reached the end
            }
            while (line != null && lineAfter != null) {
                Key keyForRecord = new Key(line, typeOfData(lineAfter));
                Record record = new Record(keyForRecord, stringData(lineAfter));
                dict.put(record);
                line = in.readLine();
                lineAfter = in.readLine();
                // we create a key with the first line which is the label and the type which is retrieved from our helper function
                // once we create the key and record with that key we use put to insert it into the dictionary.
                // then after this we read the next lines
            }
        } catch (IOException e) {
            System.out.println("Exception thrown.");
            // try file if an exception is thrown we catch it here with IO exception
        }

    }

    public int typeOfData(String line) {
        if (line != null) {
            if (line.startsWith("/")) {
                return 2;
            } else if (line.startsWith("-") || (line.startsWith("-") && (line.endsWith(".wav") || line.endsWith(".mid")))) {
                return 3;
            } else if (line.startsWith("+") || (line.startsWith("+") && (line.endsWith(".wav") || line.endsWith(".mid")))) {
                return 4;
            } else if (line.startsWith("*") || (line.startsWith("*") && (line.endsWith(".wav") || line.endsWith(".mid")))) {
                return 5;
            } else if (line.endsWith(".jpg")) {
                return 6;
            } else if (line.endsWith(".gif")) {
                return 7;
            } else if (line.endsWith(".html")) {
                return 8;
            }
        }
        // based on the cases given to us we return 1-8 based on the starts of the line and the ends of the line.
        // this is crucial as each of these gives us our types. if no sign is at the front this means it is automatically
        // type 1 unless there is .... at the end of the file
        return 1;
    }


    public String stringData(String line) {
        String stringNoType = new String(line);
        if (stringNoType != null) {
            if (stringNoType.startsWith("+") || stringNoType.startsWith("-") || stringNoType.startsWith("/") || stringNoType.startsWith("*")) {
                stringNoType = stringNoType.substring(1);
                return stringNoType;
                //for our data if we have a type at the start, we start from the index 1 since we want just the data,
                // if there is no fix before it, then we just take the line as is to retrieve it as data
            }
            return stringNoType;
            // return the string
        }
        return "";
        //else return an empty string
    }

    public void delete(String w, int k) {
        try {
            dict.remove(new Key(w, k));
        } catch (DictionaryException e) {
            System.out.println("No record in the ordered dictionary has key (" + w + ", " + k + "k)");
            // we use remove from bstDict on dict to take out the record with key w and type k else throw dict exception
        }
    }

    public void add(String w, int t, String c) throws DictionaryException {
        try {
            dict.put(new Record(new Key(w, t), c));
        } catch (DictionaryException e) {
            System.out.println("A record with the given key (" + w + ", " + t + ") is already in the ordered dictionary.");
            // we use put from bstDict on dict to add the record with key w and type k else throw dict exception
        }
    }

    public void first() {
        Record first = dict.smallest();
        String label = first.getKey().getLabel();
        int type = first.getKey().getType();
        String data = first.getDataItem();
        System.out.println(label + ", " + type + ", " + data + ".");
        // we use first from bstDict on dict to search first record else
    }

    public void last() {
        Record last = dict.largest();
        String label = last.getKey().getLabel();
        int type = last.getKey().getType();
        String data = last.getDataItem();
        System.out.println(label + ", " + type + ", " + data + ".");
        // we use last from bstDict on dict to search first record else
    }

    public static void main(String[] args) throws IOException, DictionaryException {

        if (!(args.length == 1)) {
            System.out.println("Input of files is over 1. Too many.");
            return;
        }
        // if length of files is more than 1, we cannot so we return immediately
        Interface newInterface = new Interface(args[0]);
        StringReader lineReader = new StringReader();
//  make a new interface with the file in args[0] since it is length 1 is we make it to these lines
//  use the stringReader to read through lines
        while (true) {
            String line = lineReader.read("Enter next command: ");
            if (line.equals("exit")) {
                break;
                // enter the command here if exit then break immediately
            }
            if (!(line.toLowerCase().startsWith("first")) && !(line.toLowerCase().startsWith("last")) && !(line.toLowerCase().startsWith("exit")) && !(line.toLowerCase().startsWith("define ")) &&
                    !(line.toLowerCase().startsWith("translate ")) && !(line.toLowerCase().startsWith("sound ")) && !(line.toLowerCase().startsWith("play ")) &&
                    !(line.toLowerCase().startsWith("say ")) && !(line.toLowerCase().startsWith("show ")) && !(line.toLowerCase().startsWith("animate ")) && !(line.toLowerCase().startsWith("browse ")) &&
                    !(line.toLowerCase().startsWith("add ")) && !(line.toLowerCase().startsWith("delete ")) && !(line.toLowerCase().startsWith("list "))) {
                System.out.println("Invalid Command");
                // if the commands do not start with any of the ones above, the command is invalid.
            }
            if (line.toLowerCase().startsWith("define ")) {
                String wordDefinition = line.substring("define ".length());
                Record defineRecord = newInterface.dict.get(new Key(wordDefinition, 1));
                // so if it is define, we call it type 1 and start from the string after the word define plus a space
                // to see the definition. then we call get to retrieve the record with that label and type
                try {
                    if (defineRecord != null) {
                        String defString = new String(defineRecord.getDataItem());
                        System.out.println(defString);
                    } else {
                        System.out.println("No definition for " + wordDefinition + ". Sorry");
                    }
                } catch (Exception e) {
                    System.out.println("Exception thrown.");
                }
                // if either the record is empty or does not exist we return no definition, if our try fails we have a catch statement for this
            }

            if (line.toLowerCase().startsWith("first")) {
                try {
                    newInterface.first();
                } catch (Exception e) {
                    System.out.println("Exception thrown.");
                }
                // if the command is first we return first

            }
            if (line.toLowerCase().startsWith("last")) {
                try {
                    newInterface.last();
                } catch (Exception e) {
                    System.out.println("Exception thrown.");
                    // if the command is last we return first
                }

            }
            if (line.toLowerCase().startsWith("list ")) {

               try{ String prefixPhrase = line.substring("list ".length());
                Record searchingRecord = newInterface.dict.smallest();
                //get the smallest record in the dictionary
                while (searchingRecord != null) {
                    if (searchingRecord.getKey().getLabel().startsWith(prefixPhrase.toLowerCase())) {
                        System.out.print((searchingRecord.getKey().getLabel()));
                        // compare the smallest record with every other record labels to see which matches and print
                        // if they match. keep going until null pointer is reached then exit the loop.
                        System.out.print(", ");
                    }
                    searchingRecord = newInterface.dict.successor(searchingRecord.getKey());
                }
            }catch(Exception e){
                   System.out.println("");
               }
            }


            if (line.toLowerCase().startsWith("add ")) {

                try {
                    String addPhrase = line.substring("add ".length());
                    String[] addParts = addPhrase.split(" ",3);
                    // when we split with the 3 this means after the second space it will take whatever is after and
                    //use that as the second index. hence if we do add apples 1 a food to eat, 'a food to eat
                    // belongs addparts[2].
                    if (addParts.length < 3) {
                        System.out.println("Invalid Command");
                    }
                    String word = addParts[0];
                    int Type = Integer.parseInt(addParts[1]);
                    String theData = addParts[2];
                    newInterface.dict.put(new Record(new Key(word, Type), theData));
                } catch (Exception e) {
                    System.out.println("A record with the given key (w,t) is already in the ordered dictionary");
                    // if we use add we must split it into parts to get our command, the word we want to add, the type and finally the data
                    // if the try fails we have catch exception e
                }
            }
            if (line.toLowerCase().startsWith("delete ")) {

                try {
                    if (line.startsWith("delete ")) {
                        String[] parts = line.split(" ");
                        if (parts.length == 3) {
                            String word = parts[1];
                            int type = Integer.parseInt(parts[2]);
                            newInterface.delete(word, type);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Exception thrown.");
                }
                // if we use remove we must split it into parts to get our command, the word we want to remove, the type
                // if the try fails we have catch exception e
            }

            if (line.toLowerCase().startsWith("translate ")) {
                String wordTranslated = line.substring("translate ".length());
                Record translateRecord = newInterface.dict.get(new Key(wordTranslated, 2));
                try {
                    if (translateRecord != null) {
                        System.out.println(translateRecord.getDataItem());
                    } else {
                        System.out.println("No translation for " + wordTranslated + ". Sorry");
                    }
                } catch (Exception e) {
                    System.out.println("Exception thrown.");
                }


            }
            if (line.toLowerCase().startsWith("sound ")) {
                String soundWord = line.substring("sound ".length());
                Record soundRecord = newInterface.dict.get(new Key(soundWord, 3));

                try {
                    if (soundRecord != null) {
                        SoundPlayer player = new SoundPlayer();
                        player.play(soundRecord.getDataItem());
                        // this was used from the sample file to play sound files
                        System.out.println(soundRecord.getDataItem());
                    } else {
                        System.out.println("No sound for " + soundWord + ". Sorry");
                    }
                } catch (Exception e) {
                    System.out.println("Exception thrown.");
                }

            }
            if (line.toLowerCase().startsWith("play ")) {
                String playWord = line.substring("play ".length());
                Record playRecord = newInterface.dict.get(new Key(playWord, 4));
                try {
                    if (playRecord != null) {
                        SoundPlayer song = new SoundPlayer();
                        song.play(playRecord.getDataItem());
                        System.out.println(playRecord.getDataItem());
                    } else {
                        System.out.println("No music for " + playWord + ". Sorry");
                    }
                } catch (Exception e) {
                    System.out.println("Exception thrown.");
                }

            }
            if (line.toLowerCase().startsWith("say ")) {
                String sayWord = line.substring("say ".length());
                Record sayRecord = newInterface.dict.get(new Key(sayWord, 5));
                try {
                    if (sayRecord != null) {
                        SoundPlayer voice = new SoundPlayer();
                        voice.play(sayRecord.getDataItem());
                        System.out.println(sayRecord.getDataItem());
                    } else {
                        System.out.println("No say for " + sayWord + ". Sorry");
                    }
                } catch (Exception e) {
                    System.out.println("Exception thrown.");
                }

            }
            if (line.toLowerCase().startsWith("show ")) {
                String showWord = line.substring("show ".length());
                Record showRecord = newInterface.dict.get(new Key(showWord, 6));
                try {
                    if (showRecord != null) {
                        PictureViewer viewer = new PictureViewer();
                        viewer.show(showRecord.getDataItem());
                        // this was used from the sample file to show image files
                        System.out.println(showRecord.getDataItem());
                    } else {
                        System.out.println("No image for " + showWord + ". Sorry");
                    }
                } catch (Exception e) {
                    System.out.println("Exception thrown.");
                }

            }

            if (line.toLowerCase().startsWith("animate ")) {
                String animateWord = line.substring("animate ".length());
                Record animateRecord = newInterface.dict.get(new Key(animateWord, 7));
                try {
                    if (animateRecord != null) {
                        PictureViewer viewer = new PictureViewer();
                        viewer.show(animateRecord.getDataItem());
                        System.out.println(animateRecord.getDataItem());
                    } else {
                        System.out.println("No animation for " + animateWord + ". Sorry");
                    }
                } catch (Exception e) {
                    System.out.println("Exception thrown.");
                }

            }
            if (line.toLowerCase().startsWith("browse ")) {
                String browseWord = line.substring("browse ".length());
                Record browseRecord = newInterface.dict.get(new Key(browseWord, 8));
                try {
                    if (browseRecord != null) {
                        ShowHTML browser = new ShowHTML();
                        browser.show(browseRecord.getDataItem());
                        // this was used from the sample file to browse html files
                        System.out.println(browseRecord.getDataItem());
                    } else {
                        System.out.println("No definition for " + browseWord + ". Sorry");
                    }
                } catch (Exception e) {
                    System.out.println("Exception thrown.");
                }
                // all of these commands are the same principles as defined except with different effects. The logic
                // is the exact same, we only change the type, length as well as the effect after the record not
                // being null
            }

        }

    }
}

