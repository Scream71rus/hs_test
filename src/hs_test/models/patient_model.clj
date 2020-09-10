(ns hs-test.models.patient-model
  (:require [clojure.java.jdbc :as jdbc]
            [hs-test.db :refer [db]]
            [honeysql.core :as sql]
            [honeysql.helpers :refer :all :as helpers]
            [honeysql-postgres.format]
            [honeysql-postgres.helpers :as psqlh]))

(defn query! [sql-map]
  (jdbc/query db (sql/format sql-map)))

(defn get-patients []
  (-> (select :*)
      (from :patient)
      (order-by :created)
      query!))

(defn create-patient [{:keys [first_name last_name gender birthday medical_insurance middle_name address]}]
  (-> (insert-into :patient)
      (values [{:first_name first_name
                :last_name last_name
                :gender (sql/call :cast gender :gender_type)
                :birthday (sql/call :cast birthday :timestamp)
                :medical_insurance medical_insurance
                :middle_name middle_name
                :address address}])
      (psqlh/returning :*)
      query!
      first))

(defn update-patient [id {:keys [first_name last_name middle_name gender address birthday medical_insurance]}]
  (-> (helpers/update :patient)
      (sset {:first_name first_name
             :last_name last_name
             :gender (sql/call :cast gender :gender_type)
             :birthday (sql/call :cast birthday :timestamp)
             :medical_insurance medical_insurance
             :middle_name middle_name
             :address address})
      (where [:= :id (Integer/parseInt id)])
      (psqlh/returning :*)
      query!
      first))

(defn delete-patient [id]
  (-> (delete-from :patient)
      (where [:= :id (Integer/parseInt id)])
      (psqlh/returning :id)
      query!
      first))

