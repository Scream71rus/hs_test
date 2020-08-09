(ns hs-test.models.patient-model
  (:require [clojure.java.jdbc :as jdbc]
            [hs-test.db :refer [db]]))

(defn query! [sql]
  (jdbc/query @db sql))

(defn get-patients []
  (query! ["select * from hs_test.patient"]))

(defn get-patient [patient-id]
  (query! ["select * from hs_test.patient where id = ?" patient-id]))

;; TODO добавить middle-name и address
(defn create-patient [{:keys [first-name last-name gender birthday medical-insurance]}]
   (query! ["insert into hs_test.patient(first_name, last_name, gender, birthday, medical_insurance)
            values(?, ?, ?::gender_type, ?::timestamp, ?) returning *"
              first-name last-name gender birthday medical-insurance]))

(defn update-patient [{:keys [first-name last-name middle-name gender address birthday medical-insurance]}
                      {:keys [id]}]
  (query! ["update hs_test.patient set first_name = ?, last_name = ?, middle_name = ?,
                                       gender = ?::gender_type, address = ?,
                                       birthday = ?::timestamp, medical_insurance = ?
           where id = ? returning *"
           first-name last-name middle-name gender address birthday medical-insurance (Integer/parseInt id)]))

(defn delete-patient [id]
  (query! ["delete from hs_test.patient where id = ? returning id" (Integer/parseInt id)]))
