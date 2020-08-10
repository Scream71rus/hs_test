(ns hs-test.models.patient-model
  (:require [clojure.java.jdbc :as jdbc]
            [hs-test.db :refer [db]]))

(defn query! [sql]
  (jdbc/query @db sql))

(defn get-patients []
  (query! ["select * from hs_test.patient"]))

(defn get-patient [patient-id]
  (query! ["select * from hs_test.patient where id = ?" patient-id]))

(defn create-patient [{:keys [first-name last-name gender birthday medical-insurance middle-name address]}]
   (query! ["insert into hs_test.patient(first_name, last_name, gender, birthday, medical_insurance,
                                         middle_name, address)
            values(?, ?, ?::gender_type, ?::timestamp, ?, ?, ?) returning *"
              first-name last-name gender birthday medical-insurance middle-name address]))

(defn update-patient [id {:keys [first-name last-name middle-name gender address birthday medical-insurance]}]
  (query! ["update hs_test.patient set first_name = ?, last_name = ?, middle_name = ?,
                                       gender = ?::gender_type, address = ?,
                                       birthday = ?::timestamp, medical_insurance = ?
           where id = ? returning *"
           first-name last-name middle-name gender address birthday medical-insurance (Integer/parseInt id)]))

(defn delete-patient [id]
  (query! ["delete from hs_test.patient where id = ? returning id" (Integer/parseInt id)]))
