(ns iworked.parser-test
  (:require [clojure.test :refer :all]
            [iworked.parser :refer :all]
            [clj-time.core :as t]))

(deftest worked-contains-date-and-amount
  (testing
    (let
      [date (t/now)
       amount 1
       worked (worked date amount)]
      (is (= date (:date worked)))
      (is (= amount (:amount worked)))
      )))

(deftest parser-understands-today
  (testing
    (let
      [input "today"
       midnight (t/today-at-midnight)]
      (is (= midnight (:date (parse-word input)))))))

(deftest parser-understands-yesterday
  (testing
    (let
      [yesterday-midnight (t/minus (t/today-at-midnight) (t/days 1))]
      (is (= yesterday-midnight (:date (parse-word "yesterday")))))))

(deftest parser-understands-Monday
  (testing
    (with-redefs [today (fn [] (t/date-time 2016 12 25))]
      (let
        [monday (t/date-time 2016 12 19)]
        (is (= monday (:date (parse-word "Monday"))))))))

(deftest parser-understands-lowercase-days
  (testing
    (is (not (nil? (:date (parse-word "monday")))))))

(deftest parser-returns-nil-if-unparseable
  (testing
    (is (nil? (:date (parse-word "Midweekday"))))))

(deftest parser-understands-yyyyMMdd-format
  (testing
    (is (= (t/date-time 2016 10 15) (:date (parse-word "20161015"))))))

(deftest parser-can-understands-half
  (testing
    (is (= 0.5 (:amount (parse-word "half"))))))

(deftest parser-can-understands-all
  (testing
    (is (= 1 (:amount (parse-word "all"))))))

(deftest parser-can-understands-quarter
  (testing
    (is (= 0.25 (:amount (parse-word "quarter"))))))

(deftest parser-has-defaults
  (testing
    (with-redefs [today (fn [] (t/date-time 2016 12 25))]
      (is (=
            (parse-all [])
            {:date (today) :amount 1})))))

(deftest parser-accepts-multiple-arguments
  (testing
    (with-redefs [today (fn [] (t/date-time 2016 12 25))]
      (is (=
            (parse-all ["half" "20161015"])
            {:date (t/date-time 2016 10 15) :amount 0.5})))))