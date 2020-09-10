(ns hs-test.core-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [hs-test.db :refer [reset-migrations]]
            [hs-test.core :refer :all]))

(deftest test-app

  (reset-migrations)

  (testing "create patient http"
    (let [response (app (-> (mock/request :post "/patient")
                            (mock/json-body {:first_name        "first-name@qwe.ru"
                                             :last_name         "last-name"
                                             :gender            "MALE"
                                             :birthday          "1999-10-10"
                                             :medical_insurance "medical-insurance"})))]
      (is (= (:status response) 200))
      (is (= (get-in response [:body :id]) 1))))

  (testing "create patient failed http"
    (let [data {:gender   111
                :birthday 111}
          response (app (-> (mock/request :post "/patient")
                            (mock/json-body data)))]
      (is (= (:status response) 400))

      (is (= (->> (get-in response [:body :errors])
                  keys
                  (map keyword))
             (keys data)))

      (is (= (get-in response [:body :message]) "validation error"))))

  (testing "update patient http"
    (let [response (app (-> (mock/request :put "/patient/1")
                            (mock/json-body {:first_name        "first-name@qwe.ru-update"
                                             :last_name         "last-name"
                                             :gender            "MALE"
                                             :birthday          "1999-10-10"
                                             :medical_insurance "medical-insurance"})))]
      (is (= (:status response) 200))
      (is (= (get-in response [:body :first_name]) "first-name@qwe.ru-update"))))

  (testing "update patient failed http"
    (let [data {:gender   111
                :birthday 111}
          response (app (-> (mock/request :put "/patient/1")
                            (mock/json-body data)))]

      (is (= (:status response) 400))

      (is (= (->> (get-in response [:body :errors])
                  keys
                  (map keyword))
             (keys data)))

      (is (= (get-in response [:body :message]) "validation error"))))

  (testing "get all patients http"
    (let [response (app (mock/request :get "/patient"))]
      (is (= (:status response) 200))
      (is (= (count (get-in response [:body])) 1))))

  (testing "delete patient http"
    (let [response (app (-> (mock/request :delete "/patient/1")))]
      (is (= (:status response) 200)))))
