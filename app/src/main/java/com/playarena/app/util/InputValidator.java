package com.playarena.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;
import java.util.function.Supplier;


public class InputValidator {
    private Scanner scan = new Scanner(System.in);
    private Logger logger = LoggerFactory.getLogger(InputValidator.class);

    private <T> T numTemplate(String message, Supplier<T> supplier){
        while (true) {
            try {
                System.out.println(message != "" ? (">>> " + message + ":") : ">>> Enter number: ");
                return supplier.get();
            } catch (InputMismatchException e) {
                logger.error("[-] Please enter valid number.\n");
                scan.nextLine();
            }
        }
    }
    public int getNum(String message) {
        return numTemplate(message, () -> scan.nextInt());
    }
    public double getDouble(String message) {
        return numTemplate(message, () -> scan.nextDouble());
    }

    public String getStr(String message){
        while (true) {
            System.out.println(message != "" ? (">>> " + message + ":") : ">>> Enter text:");
            String input = scan.nextLine();
            if(input.replaceAll(" ", "").matches("[a-zA-Z]+")) return input; else logger.error("[-] Inpute must contain only letters.");
        }
    }
    public String getStrEvenEmpty(String message){
        while (true) {
            System.out.println(message != "" ? (">>> " + message + ":") : ">>> Enter text:");
            String input = scan.nextLine();
            if(input.matches("[a-zA-Z]+")) return input; else logger.error("[-] Inpute must contain only letters.");
        }
    }
    public String getStr(String message, boolean includeNumbers){
        if(!includeNumbers) return getStr(message);
        while (true) {
            System.out.println(message != "" ? (">>> " + message + ":") : ">>> Enter text:");
            String input = scan.nextLine();
            if (input.replaceAll(" ", "").matches("[\\w&&[^_]]+")) return input; else logger.error("[-] Inpute must contain only letters and numbers.\n");
        }
    }

    public boolean getYesNo(String message){
        while (true) {
            System.out.println(message != "" ? (">>> " + message + " ? (y/n) : ") : ">>> You want ? (y/n) : ");
            String input = scan.nextLine();
            if (input.toLowerCase().matches("y||n")) return input.equalsIgnoreCase("y");
            else logger.error("[-] Input must be either \"y/Y\" or \"n/N\"\n");
        }
    }


    public <E extends Enum<E>> E getEnum(String message, Class<E> enumClass) {
        E[] enumConstants = enumClass.getEnumConstants();
        while (true) {
            String input = getStr(message).toUpperCase();
            Optional<E> matchedEnum = Arrays.stream(enumConstants)
                    .filter(enumConstant -> enumConstant.name().equals(input))
                    .findFirst();

            if (matchedEnum.isPresent()) {
                return matchedEnum.get();
            } else {
                logger.warn("[-] Choice must be one of the following: {}", Arrays.toString(enumConstants));
            }
        }
    }




    public Date getDate(String message, boolean mustBeAboveNow) {
        Date now = new Date();
        while (true) {
            try {
                System.out.println(message != null && !message.isEmpty()
                        ? (">>> " + message + " (format: DD/MM/YYYY):")
                        : ">>> Enter date (format: DD/MM/YYYY):");

                String input = scan.nextLine();
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                Date inputDate = format.parse(input);

                if (mustBeAboveNow && inputDate.after(now)) {
                    return inputDate;
                } else if (!mustBeAboveNow && inputDate.before(now)) {
                    return inputDate;
                } else {
                    logger.error("[-] Please enter a date that is " + (mustBeAboveNow ? "in the future." : "in the past.") + "\n");
                }
            } catch (ParseException e) {
                logger.error("[-] Invalid date, please enter a date in this format (DD/MM/YYYY)\n");
            }
        }
    }

    public void cleanBuffer(){
        scan.nextLine();
    }
}
