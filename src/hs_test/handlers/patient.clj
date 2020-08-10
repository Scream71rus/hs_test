(ns hs-test.handlers.patient
  (:require [compojure.core :refer :all]
            [hs-test.models.patient-model :refer :all]
            [clojure.spec.alpha :as s]))

(s/def ::first-name string?)
(s/def ::last-name string?)
(s/def ::gender (s/and string? (partial re-matches #"^(MALE|FEMALE)$")))
(s/def ::birthday (s/and string? (partial re-matches #"^(20|19)?\d{2}-\d{2}-\d{2}$")))
(s/def ::medical-insurance string?)
(s/def ::middle-name string?)
(s/def ::address string?)
(s/def ::id (s/and string? (partial re-matches #"[0-9]+")))

(s/def ::create-validation-schema (s/keys :req-un [::first-name ::last-name ::gender
                                                   ::birthday ::medical-insurance]
                                          :opt-un [::address ::middle-name]))
(s/def ::update-validation-schema (s/keys :req-un [::first-name ::last-name ::gender
                                                   ::birthday ::medical-insurance
                                                   ::id]
                                          :opt-un [::address ::middle-name]))
(s/def ::delete-validation-schema (s/keys :req-un [::id]))

(def create-validate-map {:first-name        "first-name is not valid"
                          :last-name         "last-name is not valid"
                          :gender            "gender is not valid"
                          :birthday          "birthday is not valid"
                          :medical-insurance "medical-insurance is not valid"})

(def update-validate-map {:id                "id is not valid"
                          :first-name        "first-name is not valid"
                          :last-name         "last-name is not valid"
                          :gender            "gender is not valid"
                          :birthday          "birthday is not valid"
                          :medical-insurance "medical-insurance is not valid"})

(def delete-validate-map {:id "id is not valid"})

(defn validate [data scheme validate-map]
  (->> (s/explain-data scheme data)
       :clojure.spec.alpha/problems
       (map #(get-in % [:path 0]))
       (select-keys validate-map)))

(defn make-response [code body]
  {:status code
   :body   body})

(defn get-handler []
  {:status 200
   :body   {:ok   true
            :data (get-patients)}})

(defn post-handler [{:keys [body]}]
  (let [validation-errors (validate body ::create-validation-schema create-validate-map)]

    (if (empty? validation-errors)
      (try
        (make-response 200 (create-patient body))
        (catch Exception e (make-response 500 {:message e})))
      (make-response 400 {:message "validation error"
                          :errors  validation-errors}))))

(defn put-handler [{:keys [body route-params]}]
  (let [validation-errors (validate (merge body route-params) ::update-validation-schema update-validate-map)]

    (if (empty? validation-errors)
      (try
        (make-response 200 (update-patient (route-params :id) body))
        (catch Exception e (make-response 500 {:message e})))
      (make-response 400 {:message "validation error"
                          :errors  validation-errors}))))

(defn delete-handler [id]
  (let [validation-errors (validate {:id id} ::delete-validation-schema delete-validate-map)]

    (if (empty? validation-errors)
      (try
        (make-response 200 (delete-patient id))
        (catch Exception e (make-response 500 {:message e})))
      (make-response 400 {:message "validation error"
                          :errors  validation-errors}))))

(defroutes patient
           (GET "/patient" [] (get-handler))
           (POST "/patient" request (post-handler request))
           (PUT "/patient/:id" request (put-handler request))
           (DELETE "/patient/:id" [id] (delete-handler id)))
