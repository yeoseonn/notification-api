## 20240520_2299-000143_윤여선_서버 개발자 - 백엔드 API




## API SPEC
### 알림발송등록 API 
#### POST /notifications
* request body 
  * 즉시 발송 
    ```json5
    {
        "memberId": 1004,
        "type": "immediate",
        "title":"test",
        "content": "알림"
    }
    ```
  * 예약 발송
    ```json5
    {
        "memberId": 1004,
        "type": "scheduled",
        "time" : "2024-05-20 11:00:00",
        "title":"test",
        "content": "알림"
    }
    ```
    - 예약 발송일 경우, time이 null이거나 현재 시간보다 이전 인 경우는 실패.
* response
    ```json5
    {
        "header": {
            "isSuccessful": true,
            "resultCode": 0,
            "resultMessage": ""
        },
        "result": {
            "notificationId": 1757221248903485,
            "memberId": 1004,
            "type": "immediate",
            "time": null,
            "title": "test",
            "content": "알림 1222내용"
        }
    }
    ```
#### member정보를 찾을 수 없는 경우 
* Http Status Code : 404 NotFound 
* response

    ```json5
    {
        "header": {
            "isSuccessful": false,
            "resultCode": -1005,
            "resultMessage": "cannot find member Id : 22"
        },
        "result": null
    }
    ```

### 알림내역조회 API 
#### GET /notifications/logs?memberId=1004&page=0&size=20
* defaultSize : 20 
* response
    ```json5
    {
      "header": {
        "isSuccessful": true,
        "resultCode": 0,
        "resultMessage": ""
      },
      "totalCount": 3,
      "result": [
        {
          "notificationLogId": 1757221245067772,
          "notificationId": 1757221246492925,
          "memberId": 1004,
          "senderType": "SMS",
          "sentAt": "2024-05-18T21:46:10.04978"
        },
        {
          "notificationLogId": 1757221247911015,
          "notificationId": 1757221246922801,
          "memberId": 1004,
          "senderType": "KAKAO_TALK",
          "sentAt": "2024-05-18T21:46:14.323453"
        },
        {
          "notificationLogId": 1757221250867830,
          "notificationId": 1757221248903485,
          "memberId": 1004,
          "senderType": "KAKAO_TALK",
          "sentAt": "2024-05-18T21:46:17.239747"
        }
      ]
    }
    ```

## 프로젝트 설명



## 빌드 결과물 다운로드 링크 