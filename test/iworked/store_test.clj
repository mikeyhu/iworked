(ns iworked.store-test
  (:require [clojure.test :refer :all]
            [iworked.store :refer :all]
            [iworked.event :as e]
            [clj-time.core :as t]))

(defn uuid [] (str (java.util.UUID/randomUUID)))

(deftest can-save-and-load-some-data
  (testing "saving and loading"
    (let [test-file (str "/tmp/" (uuid) ".cljdata")
          test-data `(1 2 3)]
      (save-to test-file test-data)
      (is (= test-data (load-from test-file))))))

(deftest loads-nil-from-not-existing-file
  (testing "saving and loading"
    (let [test-file (str "/tmp/" (uuid) ".cljdata")]
      (is (= {} (load-from test-file))))))

(deftest checks-file-for-existance
  (testing
    (is (file-exists "/etc"))))

(deftest checks-file-for-non-existance
  (testing
    (is (not (file-exists "/bob")))))

(deftest can-save-an-event
  (testing
    (let [events [(e/event (t/date-time 2016 11 11) 1)]
          test-file (str "/tmp/" (uuid) ".cljdata")]
      (save-events test-file events)
      (is (= events (load-events test-file))))))

(deftest can-add-an-event
  (testing
    (let [event (e/event (t/date-time 2016 11 11) 1)
          events {"20161111" 1}]
      (is (= events (add-event-to {} event))))))

(deftest can-add-an-event-to-another
  (testing
    (let [event (e/event (t/date-time 2016 11 12) 2)
          events {"20161111" 1}
          expected {"20161111" 1, "20161112" 2}]
      (is (= expected (add-event-to events event))))))

(deftest should-not-store-the-same-event-twise
  (testing
    (let [event (e/event (t/date-time 2016 11 11) 1)
          events {"20161111" 1}]
      (is (= events (add-event-to events event))))))
