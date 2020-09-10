(ns hs-test.models.patient-model-test
  (:require [clojure.test :refer :all]
            [hs-test.db :refer [db reset-migrations]]
            [hs-test.models.patient-model :refer :all]))

(deftest test-app

  (reset-migrations)

  (testing "create patient model"
    (let [patient (create-patient {:first_name        "first-name@qwe.ru"
                                   :last_name         "last-name"
                                   :gender            "MALE"
                                   :birthday          "1999-10-10"
                                   :medical_insurance "medical-insurance"})]
      (is (= (:id patient) 1))))

  (testing "update patient model"
    (let [patient (update-patient "1" {:first_name        "update-name@qwe.ru"
                                       :last_name         "last-name"
                                       :gender            "MALE"
                                       :birthday          "1999-10-10"
                                       :medical_insurance "medical-insurance"})]
      (is (= (:first_name patient) "update-name@qwe.ru"))))

  (testing "get patients model"
    (let [patient (get-patients)]
      (is (= (count patient) 1))))

  (testing "delete patients model"
    (let [patient (delete-patient "1")]
      (is (= (:id patient) 1)))

    (let [patient (get-patients)]
      (is (= (count patient) 0)))))
