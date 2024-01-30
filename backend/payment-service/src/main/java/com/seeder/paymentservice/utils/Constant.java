package com.seeder.paymentservice.utils;

public class Constant {

  private Constant() {}

  public static final String OUTSTANDING_AMOUNT_ERROR =
    "provide valid outstanding amount";
  public static final String STATUS_ERROR =
    "status should be within 6-10 characters only";
  public static final String DAYS_REMAINING_MESSAGE = "day(s) from now";

  public static final String API_ENDPOINT = "/api/v1/payments";
}
