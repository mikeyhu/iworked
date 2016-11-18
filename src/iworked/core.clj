(ns iworked.core
  (:require [iworked.parser :as p]
            [iworked.store :as s]
            [iworked.event :as e]
            [clj-time.format :as f])
  (:gen-class))

(def location ".iworked.cljdata")

(defn -main
  [& args]
  (let
    [data (s/load-from location)]
    (s/save-to location
               (s/add-event-to data (p/parse-word (first args))))))

