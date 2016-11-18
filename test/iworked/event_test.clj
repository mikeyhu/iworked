(ns iworked.event-test
  (:require [clojure.test :refer :all]
            [iworked.event :refer :all]
            [clj-time.core :as t]))

(deftest event-can-have-dates-serialized
  (testing
    (let [event (event (t/date-time 2016 11 01) 1)
          expected "20161101"]
      (is (= expected (:date (to-saveable event)))))))

(deftest event-can-have-dates-deserialized
  (testing
    (let [event (event "20161101" 1)
          expected (t/date-time 2016 11 01)]
      (is (= expected (:date (from-saveable event)))))))