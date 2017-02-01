(ns iworked.report-test
  (:require [clojure.test :refer :all]
            [iworked.report :refer :all]
            [clj-time.core :as t]
            [iworked.date :as d]))

(deftest finds-days-in-month
  (testing
    (let [date (t/date-time 2016 01)
          first-of-month (t/date-time 2016 01 01)
          last-of-month (t/date-time 2016 01 31)
          days (days-in-month date)]
      (is (= 31 (count days)))
      (is (= first-of-month (first days)))
      (is (= last-of-month (last days))))))

(deftest only-shows-days-in-the-past
  (testing
    (with-redefs [d/today (fn [] (t/date-time 2016 12 25))]
      (let [date (t/date-time 2016 12)
            days (days-in-month date)]
        (is (= (t/date-time 2016 12 25) (last days)))
        ))))

(deftest returns-report
  (testing
    (with-redefs [d/today (fn [] (t/date-time 2016 11 05))]
      (let [data {"20161101" 1, "20161102" 1, "20161103" 0.5}
            days (days-in-month (t/date-time 2016 11))]
        (is (= [
                {:date (t/date-time 2016 11 01) :amount 1}
                {:date (t/date-time 2016 11 02) :amount 1}
                {:date (t/date-time 2016 11 03) :amount 0.5}
                {:date (t/date-time 2016 11 04) :amount nil}
                {:date (t/date-time 2016 11 05) :amount nil}]
               (generate-report days data)))
        ))))

(deftest selects-appropriate-colour
  (testing
    (let [weekday (t/date-time 2016 12 01)
          weekend (t/date-time 2016 12 03)]
      (is (= :green (display-colour {:date weekday :amount 1})))
      (is (= :cyan (display-colour {:date weekday :amount 0.5})))
      (is (= :blue (display-colour {:date weekday :amount 0.25})))
      (is (= :red (display-colour {:date weekday :amount 0})))
      (is (= :green (display-colour {:date weekend :amount 1})))
      (is (= :cyan (display-colour {:date weekend :amount 0.5})))
      (is (= :blue (display-colour {:date weekend :amount 0.25})))
      (is (= :white (display-colour {:date weekend :amount 0})))
      )))

(deftest select-current-month-if-no-arguments
  (testing
    (with-redefs [d/today (fn [] (t/date-time 2016 11 05))]
      (let [data {"20161101" 1, "20161102" 1, "20161103" 0.5}]
        (is (= [
                {:date (t/date-time 2016 11 01) :amount 1}
                {:date (t/date-time 2016 11 02) :amount 1}
                {:date (t/date-time 2016 11 03) :amount 0.5}
                {:date (t/date-time 2016 11 04) :amount nil}
                {:date (t/date-time 2016 11 05) :amount nil}]
               (choose-report [] data)))
        ))))

(deftest select-appropriate-month-if-provided
  (testing
    (with-redefs [d/today (fn [] (t/date-time 2016 12 05))]
      (let [data {"20161101" 1, "20161102" 1, "20161103" 0.5}]
        (is (=  {:date (t/date-midnight 2016 11 01) :amount 1}
                (first (choose-report ["november"] data))))
        ))))