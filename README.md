# Feature flag service

### Entities

1. Flag: represents the feature flag or experiment.
2. Variant: Possible values of the flag.
3. Segment: represents the segmentation of traffic.
   1. Constraint: represents the constraint for a segment. The segment will be picked only when this constraint passes. The service support dynamic constraints also where user provides the variables in the evaluate API.
4. Distribution: represents the distribution of variants in a segment.


Note: 
1. Default variant is `control`. API will return `control` if no segment passes.
2. Variants are deterministically returned. The service should not return different variants on same id.

### Rollout process

1. Iterate over segments and check the constraints using the context provided in the API call.
2. Take the CRC hash of the entity id provided in the request.
3. Take the hash and mod by total numbers of bucket (10000).
4. Check the distribution. e.g. 30/70 split for `on` and `off` means 0-2999 for `on` and rest for `off`.
5. Consider the rollout percentage for the segment.


### How to define Constraints

The service uses [Spring Expression Language](https://docs.spring.io/spring-framework/docs/3.0.x/reference/expressions.html) for evaluating constraints. It supports variables.

e.g. constraint `#userId == 3` will match when `userId=3` in the request.


### Prerequisites

1. MySQL DB running on port 3306. DB with name `flagger` should be present.

### Running the service

`mvn clean install && java -jar target/flagger-0.0.1-SNAPSHOT.jar`

### cURL


1. Creating feature flag

```
curl --location --request POST 'localhost:8080/api/v1/flag' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "test",
    "description": "FF for testing",
    "enabled": true,
    "variants": [
        "on",
        "off"
    ]
}'
```

2. Create segments
```{
    "segments": [
        {
            "name": "segment1",
            "priority": 1,
            "rolloutPercentage": 100,
            "constraint": "#userId == 123",
            "distributions": [
                {
                    "name": "d1",
                    "percent": 30,
                    "variant": "on"
                },
                {
                    "name": "d2",
                    "percent": 70,
                    "variant": "off"
                }
            ]
        },
        {
            "name": "segment2",
            "priority": 2,
            "rolloutPercentage": 50,
            "constraint": "#userId == 456",
            "distributions": [
                {
                    "name": "d3",
                    "percent": 50,
                    "variant": "on"
                },
                {
                    "name": "d4",
                    "percent": 50,
                    "variant": "off"
                }
            ]
        }
    ]
}
```

3. Evaluate feature flag

```
curl --location --request GET 'localhost:8080/api/v1/evaluate' \
--header 'Content-Type: application/json' \
--data-raw '{
    "context": {
        "userId": 123
    },
    "id": "random",
    "flagName": "test"
}'
```