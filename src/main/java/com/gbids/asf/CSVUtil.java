package com.gbids.asf;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.apache.commons.csv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVUtil {

    private static final Logger logger = LoggerFactory.getLogger(CSVUtil.class);

    List<String> headerList = new ArrayList<>();

    {
        headerList.add("Name");
        headerList.add("Age");
        headerList.add("Email");
    }

    //////////////////////// Read related methods ///////////////////////

    public void readCSVFileSimpleOne() {
        // happy path one
        try {
            // change your csv file path properly
            Reader in = new FileReader("D:\\code\\apache\\csv\\samples\\basicCsvSample-1\\src\\main\\resources\\samplCsv.csv");

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(headerList.toArray(new String[0]))
                    .setSkipHeaderRecord(true)
                    .build();

            Iterable<CSVRecord> records = csvFormat.parse(in); // return a CSVParser - which is an Iterable

            for (CSVRecord record : records) {
                String name = record.get(headerList.get(0));
                String age = record.get(headerList.get(1));
                String email = record.get(headerList.get(2));
                logger.info("Name: {}, Age: {}, Email: {}", name, age, email);
            }
        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public void readCSVFileSimpleTwo() {
//      attempt to read csv file which has header value which has duplicated header values
        try {
//          change your csv file path properly
            Reader in = new FileReader("D:\\code\\apache\\csv\\samples\\basicCsvSample-1\\src\\main\\resources\\samplCsv_headerDuplicate.csv");

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
//                    .setHeader(headerList.toArray(new String[0])) -> If you set the header values as this then there is no point of using the parser options such as 'setAllowMissingColumnNames'
//                    In that case what we are tying to achieve is that, we hardcode the header values then validating them against the rules - makes no sense
                    .setHeader() // auto-detect the header values
                    .setSkipHeaderRecord(true)
                    .setDuplicateHeaderMode(DuplicateHeaderMode.DISALLOW) // Disallow any headers which are duplicate
//                    .setDuplicateHeaderMode(DuplicateHeaderMode.ALLOW_EMPTY) // Allow duplicates if they are only white spaces
//                    .setDuplicateHeaderMode(DuplicateHeaderMode.ALLOW_ALL) // Allow all
                    .build();

            Iterable<CSVRecord> records = csvFormat.parse(in); // return a CSVParser - which is an Iterable

        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public void readCSVFileSimpleThree() {
//      attempting to read CSV file which does not contain a header value - missing header filed
        try {
            // change your csv file path properly
            Reader in = new FileReader("D:\\code\\apache\\csv\\samples\\basicCsvSample-1\\src\\main\\resources\\csv_missingHeaderValue.csv");

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
//                  .setHeader(headerList.toArray(new String[0])) -> If you set the header values as this then there is no point of using the parser options such as 'setAllowMissingColumnNames'
//                  In that case what we are tying to achieve is that, we hardcode the header values then validating them against the rules - makes no sense
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setAllowMissingColumnNames(false)
                    .build();

            Iterable<CSVRecord> records = csvFormat.parse(in); // return a CSVParser - which is an Iterable

            for (CSVRecord record : records) {
                String name = record.get("Name");
                String age = record.get(headerList.get(1));
                String email = record.get(headerList.get(2));
                logger.info("Name: {}, Age: {}, Email: {}", name, age, email);
            }

        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public void readCSVFileSimpleFour() {
        // attempting to read CSV file which contains comments in the file
        try {
            // change your csv file path properly
            Reader in = new FileReader("D:\\code\\apache\\csv\\samples\\basicCsvSample-1\\src\\main\\resources\\csv_comments.csv");

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
//                  .setHeader(headerList.toArray(new String[0])) -> If you set the header values as this then there is no point of using the parser options such as 'setAllowMissingColumnNames'
//                  In that case what we are tying to achieve is that, we hardcode the header values then validating them against the rules - makes no sense
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setCommentMarker('#') // if you do not set this in then '# Amal' will be considered as one string.
                    .build();

            Iterable<CSVRecord> records = csvFormat.parse(in); // return a CSVParser - which is an Iterable

            for (CSVRecord record : records) {
                String name = record.get("Name");
                String age = record.get("Age");
                String email = record.get("Email");
                logger.info("Name: {}, Age: {}, Email: {}", name, age, email);
            }

        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public void readCSVFileSimpleFive() {
        // attempting to read CSV file which contains escape character in a different way
        try {
            // change your csv file path properly
            Reader in = new FileReader("D:\\code\\apache\\csv\\samples\\basicCsvSample-1\\src\\main\\resources\\csv_escape_diff_2.csv");

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
//                  .setHeader(headerList.toArray(new String[0])) -> If you set the header values as this then there is no point of using the parser options such as 'setAllowMissingColumnNames'
//                  In that case what we are tying to achieve is that, we hardcode the header values then validating them against the rules - makes no sense
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setEscape('-') // Default escape for csv files is the double quote "
                    .setEscape('\'') // Default escape for csv files is the double quote " -> use 'csv_escape_diff.csv' to test.
                    .build();

            Iterable<CSVRecord> records = csvFormat.parse(in); // return a CSVParser - which is an Iterable

            for (CSVRecord record : records) {
                String name = record.get("Name");
                String age = record.get("Age");
                String email = record.get("Email");
                logger.info("Name: {}, Age: {}, Email: {}", name, age, email);
            }

        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public void readCSVFileSimpleSix() {
//      attempting to read CSV file which contains empty lines
        try {
            // change your csv file path properly
            Reader in = new FileReader("D:\\code\\apache\\csv\\samples\\basicCsvSample-1\\src\\main\\resources\\csv_emptyLines.csv");

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
//                  .setHeader(headerList.toArray(new String[0])) -> If you set the header values as this then there is no point of using the parser options such as 'setAllowMissingColumnNames'
//                  In that case what we are tying to achieve is that, we hardcode the header values then validating them against the rules - makes no sense
                    .setHeader()
                    .setIgnoreEmptyLines(false) // default is true
                    .setSkipHeaderRecord(true)
                    .build();

            Iterable<CSVRecord> records = csvFormat.parse(in); // return a CSVParser - which is an Iterable

            for (CSVRecord record : records) {
                String name = record.get("Name");
                logger.info("Name: {}", name);
            }

        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public void readCSVFileSimpleSeven() {
//      attempting to read CSV file which contains spaces in the tokens
        try {
            // change your csv file path properly
            Reader in = new FileReader("D:\\code\\apache\\csv\\samples\\basicCsvSample-1\\src\\main\\resources\\csv_spaces.csv");

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
//                  .setHeader(headerList.toArray(new String[0])) -> If you set the header values as this then there is no point of using the parser options such as 'setAllowMissingColumnNames'
//                  In that case what we are tying to achieve is that, we hardcode the header values then validating them against the rules - makes no sense
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setIgnoreSurroundingSpaces(true)
                    .build();

            Iterable<CSVRecord> records = csvFormat.parse(in); // return a CSVParser - which is an Iterable

            for (CSVRecord record : records) {
                String name = record.get("Name");
                String age = record.get("Age");
                String email = record.get("Email");
                logger.info("Name: {}, Age: {}, Email: {}", name, age, email);
            }

        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public void readCSVFileSimpleEight() {
//      attempting to read CSV file which contains ABC which will be read as null
        try {
            // change your csv file path properly
            Reader in = new FileReader("D:\\code\\apache\\csv\\samples\\basicCsvSample-1\\src\\main\\resources\\csvNull.csv");

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
//                    .setHeader(headerList.toArray(new String[0])) -> If you set the header values as this then there is no point of using the parser options such as 'setAllowMissingColumnNames'
//                    In that case what we are tying to achieve is that, we hardcode the header values then validating them against the rules - makes no sense
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setNullString("ABC")
                    .build();

            Iterable<CSVRecord> records = csvFormat.parse(in); // return a CSVParser - which is an Iterable

            for (CSVRecord record : records) {
                String name = record.get("Name");
                String age = record.get("Age");
                String email = record.get("Email");
                logger.info("Name: {}, Age: {}, Email: {}", name, age, email);
            }

        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public void readCSVFileSimpleNine() {
        // attempting to read CSV file with quoting (not working for the reading)
        try {
            // change your csv file path properly
            Reader in = new FileReader("D:\\code\\apache\\csv\\samples\\basicCsvSample-1\\src\\main\\resources\\csv_normal.csv");

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
//                    .setHeader(headerList.toArray(new String[0])) -> If you set the header values as this then there is no point of using the parser options such as 'setAllowMissingColumnNames'
//                    In that case what we are tying to achieve is that, we hardcode the header values then validating them against the rules - makes no sense
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setQuote('\'') // Quoting only works in writing mode
                    .setQuoteMode(QuoteMode.ALL)
                    .build();

            Iterable<CSVRecord> records = csvFormat.parse(in); // return a CSVParser - which is an Iterable

            for (CSVRecord record : records) {
                String name = record.get("Name");
                String age = record.get("Age");
                String email = record.get("Email");
                logger.info("Name: {}, Age: {}, Email: {}", name, age, email);
//                logger.info("Name: {}, Age: {}, Email: {}", name);
            }

        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    ////////////////////// Write related methods /////////////////////////

    public void writeCSVFileSimpleOne() {
        // Attempting to write a simple CSV file
        MultiValuedMap<String, String> multiValuedMap = new HashSetValuedHashMap();
        multiValuedMap.putAll("UserOne", Arrays.asList("35", "userOne@ymail.com"));
        multiValuedMap.putAll("UserTwo", Arrays.asList("36", "userTwo@ymail.com"));
        multiValuedMap.putAll("UserThree", Arrays.asList("37", "userThree@ymail.com"));

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(headerList.toArray(new String[0]))
                .setDelimiter(";")
                .build();

        StringWriter sw = new StringWriter();

        try (final CSVPrinter printer = new CSVPrinter(sw, csvFormat)) {
            multiValuedMap.keySet().forEach(name -> {
                String[] array = multiValuedMap.get(name).toArray(new String[0]);
                String age = array[0];
                String email = array[1];
                try {
                    printer.printRecord(name, age, email);
                } catch (IOException e) {
                    logger.error("Error while writing to the file", e);
                }
            });
        } catch (IOException ex) {
            logger.error("Error occurred while trying to write content to the file", ex);
        }
        logger.info("File content: {}", sw);
    }

    public void writeCSVFileSimpleTwo() {
        // Attempting to write to a CSV file with duplicate header values
        MultiValuedMap<String, String> multiValuedMap = new HashSetValuedHashMap();
        multiValuedMap.putAll("UserOne", Arrays.asList("35", "userOne@ymail.com"));
        multiValuedMap.putAll("UserTwo", Arrays.asList("36", "userTwo@ymail.com"));
        multiValuedMap.putAll("UserThree", Arrays.asList("37", "userThree@ymail.com"));

        headerList = new ArrayList<>();
        headerList.add("Name");
        headerList.add("Age");
        headerList.add("Age");
        headerList.add("Email");

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(headerList.toArray(new String[0]))
                .setDelimiter(";")
                .setDuplicateHeaderMode(DuplicateHeaderMode.DISALLOW)
                .build();

        StringWriter sw = new StringWriter();

        try (final CSVPrinter printer = new CSVPrinter(sw, csvFormat)) {
            multiValuedMap.keySet().forEach(name -> {
                String[] array = multiValuedMap.get(name).toArray(new String[0]);
                String age = array[0];
                String email = array[1];
                try {
                    printer.printRecord(name, age, email);
                } catch (IOException e) {
                    logger.error("Error while writing to the file", e);
                }
            });
        } catch (IOException ex) {
            logger.error("Error occurred while trying to write content to the file", ex);
        }
        logger.info("File content: {}", sw);
    }

    public void writeCSVFileSimpleThree() {
        // Attempting to write to a CSV file with header values and comment marker
        MultiValuedMap<String, String> multiValuedMap = new HashSetValuedHashMap();
        multiValuedMap.putAll("UserOne", Arrays.asList("35", "userOne@ymail.com"));
        multiValuedMap.putAll("UserTwo", Arrays.asList("36", "userTwo@ymail.com"));
        multiValuedMap.putAll("UserThree", Arrays.asList("37", "userThree@ymail.com"));

        headerList = new ArrayList<>();
        headerList.add("Name");
        headerList.add("Age");
        headerList.add("Email");

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(headerList.toArray(new String[0]))
                .setDelimiter(",")
                .setHeaderComments("Sample header comment one", "Sample header comment two")
                .setCommentMarker('-')
                .build();
        // ToDo: Check the internals of setHeaderComments - this does not give any error when CommentMarker is not set. This could have been a simple line in documentation.
        // Or an illegalStateException

        StringWriter sw = new StringWriter();

        try (final CSVPrinter printer = new CSVPrinter(sw, csvFormat)) {
            multiValuedMap.keySet().forEach(name -> {
                String[] array = multiValuedMap.get(name).toArray(new String[0]);
                String age = array[0];
                String email = array[1];
                try {
                    printer.printRecord(name, age, email);
                } catch (IOException e) {
                    logger.error("Error while writing to the file", e);
                }
            });
        } catch (IOException ex) {
            logger.error("Error occurred while trying to write content to the file", ex);
        }
        logger.info("File content: {}", sw);
    }

    public void writeCSVFileSimpleFour() {
        // Attempting to write to a CSV file with null strings
        MultiValuedMap<String, String> multiValuedMap = new HashSetValuedHashMap();
        multiValuedMap.putAll("UserOne", Arrays.asList("35", "userOne@ymail.com"));
        multiValuedMap.putAll("ABC", Arrays.asList("36", "ABC"));
        multiValuedMap.putAll("UserThree", Arrays.asList("37", "userThree@ymail.com"));

        headerList = new ArrayList<>();
        headerList.add("Name");
        headerList.add("Age");
        headerList.add("Email");

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(headerList.toArray(new String[0]))
                .setDelimiter(",")
                .setHeaderComments("Sample header comment one", "ABC")
                .setCommentMarker('-')
                .setNullString("ABC")
                .build();
        // ToDo: As for the documentation, ABC should be printed as null in the output. However, it is printing as it is.
        // Is this correct ?

        StringWriter sw = new StringWriter();

        try (final CSVPrinter printer = new CSVPrinter(sw, csvFormat)) {
            multiValuedMap.keySet().forEach(name -> {
                String[] array = multiValuedMap.get(name).toArray(new String[0]);
                String age = array[0];
                String email = array[1];
                try {
                    printer.printRecord(name, age, email);
                } catch (IOException e) {
                    logger.error("Error while writing to the file", e);
                }
            });
        } catch (IOException ex) {
            logger.error("Error occurred while trying to write content to the file", ex);
        }
        logger.info("File content: {}", sw);
    }

    public void writeCSVFileSimpleFive() {
        // Attempting to write to a CSV file quotes, delimiter, header comments
        MultiValuedMap<String, String> multiValuedMap = new HashSetValuedHashMap();
        multiValuedMap.putAll("UserOne", Arrays.asList("35", "userOne@ymail.com"));
        multiValuedMap.putAll("UserTwo", Arrays.asList("36", "usertwo@ymail.com"));
        multiValuedMap.putAll("UserThree", Arrays.asList("37", "userThree@ymail.com"));

        headerList = new ArrayList<>();
        headerList.add("Name");
        headerList.add("Age");
        headerList.add("Email");

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(headerList.toArray(new String[0]))
                .setDelimiter(",")
                .setHeaderComments("Sample header comment one", "ABC")
                .setCommentMarker('-')
                .setQuote('\'')
                .setQuoteMode(QuoteMode.ALL)
                .build();

        StringWriter sw = new StringWriter();

        try (final CSVPrinter printer = new CSVPrinter(sw, csvFormat)) {
            multiValuedMap.keySet().forEach(name -> {
                String[] array = multiValuedMap.get(name).toArray(new String[0]);
                String age = array[0];
                String email = array[1];
                try {
                    printer.printRecord(name, age, email);
                } catch (IOException e) {
                    logger.error("Error while writing to the file", e);
                }
            });
        } catch (IOException ex) {
            logger.error("Error occurred while trying to write content to the file", ex);
        }
        logger.info("File content: {}", sw);
    }
}
