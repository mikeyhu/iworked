(ns iworked.store-test
  (:require [clojure.test :refer :all]
            [iworked.store :refer :all]))

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
      (is (= [] (load-from test-file))))))

(deftest checks-file-for-existance
  (testing
    (is (file-exists "/etc"))))

(deftest checks-file-for-non-existance
  (testing
    (is (not (file-exists "/bob")))))
