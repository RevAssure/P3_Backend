# RevAssure REST API Guide

## Table of Contents
### 1. [/revuser](#revuser)
### 2. [/curriculum](#curriculum)
### 3. [/technology_category](#technology_category)
### 4. [/module](#module)
### 5. [/topic](#topic)
### 6. [/event](#event)

---

## ```/revuser```

### ```/revuser/register```

  - ### ```POST``` - **Create a new trainer or a new associate.**
    
> **Request Body:**
> 
> **Trainer:**
>```json
>{
>    "username": "biosman",
>    "password": "secret",
>    "firstName": "Gary",
>    "lastName": "Kildall",
>    "trainer": true
>}
>```
> ___OR___
> 
> **Associate:**
>```json
>{
>    "username": "billyg",
>    "password": "secret",
>    "firstName": "Bill",
>    "lastName": "Gates",
>    "trainer": false
>}
>```

> **Response Body:**
>
>```json
>{
>    "id": 1,
>    "username": "biosman",
>    "firstName": "Gary",
>    "lastName": "Kildall",
>    "topics": null,
>    "curricula": null,
>    "modules": null,
>    "ownedCurricula": null,
>    "trainer": true
>}
>```
>___OR___
>
>```json
>{
>    "id": 2,
>    "username": "billyg",
>    "firstName": "Bill",
>    "lastName": "Gates",
>    "topics": null,
>    "curricula": null,
>    "modules": null,
>    "ownedCurricula": null,
>    "trainer": false
>}
>```

### ```/revuser/register```

- ### ```POST``` - **Send the login credentials here to see if they are registered. Receive a JWT for all subsequent requests. Receive a 403 if the login credentials are not valid.**
> **Request Body:**
>
>```json
>{
>    "username": "biosman",
>    "password": "secret"
>}
>```
>
>**Response Body:**
>```json
>{
>    "jwt": "####.####.####"
>}
>```

___The rest of this guide will show ####.####.#### whenever the JWT is expected, which is in the header of ALL requests EXCEPT to `/revuser/register` & `/revuser/authenticate`___

## ```/revuser``` 
__(continued)__

- ### ```GET``` - **Acquire the user object.**

>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```

> **Response Body:**
>
>```json
>{
>    "id": 1,
>    "username": "biosman",
>    "firstName": "Gary",
>    "lastName": "Kildall",
>    "topics": [],
>    "curricula": [],
>    "modules": [],
>    "ownedCurricula": [],
>    "trainer": true
>}
>```
---

## ```/curriculum```

- ### ```POST``` - **Create a new curriculum.**

>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```

> **Request Body:**
>```json
>{
>    "name": "Basic Input Output System",
>    "associates": [2]  
>}
>```
>> `associates` **array of ids of the associates who will need access to this curriculum.**

> **Response Body:**
>
>```json
>{
>    "id": 1,
>    "name": "Basic Input Output System",
>    "trainer": {
>        "id": 1,
>        "username": "biosman",
>        "firstName": "Gary",
>        "lastName": "Kildall",
>        "topics": [],
>        "curricula": [],
>        "modules": [],
>        "trainer": true
>    },
>    "events": [],
>    "revUsers": [
>        {
>            "id": 2,
>            "username": null,
>            "firstName": null,
>            "lastName": null,
>            "topics": null,
>            "modules": null,
>            "ownedCurricula": null,
>            "trainer": false
>        }
>    ]
>}
>```

- ### ```PUT``` - **Update an existing curriculum.**

>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```

> **Request Body:**
>```json
>{
>    "id": 1,
>    "name": "BIOS",
>    "associates": [2, 3]  
>}
>```
>> ___All fields are required with PUT request, otherwise they will become `null`.___
>>
>> `associates` **array of ids of the associates who will need access to this curriculum.** 

> **Response Body:**
>
>```json
>{
>    "id": 1,
>    "name": "BIOS",
>    "trainer": {
>        "id": 1,
>        "username": "biosman",
>        "firstName": "Gary",
>        "lastName": "Kildall",
>        "topics": [],
>        "curricula": [],
>        "modules": [],
>        "trainer": true
>    },
>    "events": [],
>    "revUsers": [
>        {
>            "id": 2,
>            "username": "billyg",
>            "firstName": "Bill",
>            "lastName": "Gates",
>            "topics": [],
>            "modules": [],
>            "ownedCurricula": [],
>            "trainer": false
>        },
>        {
>            "id": 3,
>            "username": "dosman",
>            "firstName": "Tim",
>            "lastName": "Paterson",
>            "topics": [],
>            "modules": [],
>            "ownedCurricula": [],
>            "trainer": false
>        }
>    ]
>}
>```

- ### ```GET``` - **Get all curriculum owned by whoever sends the request. Only trainers own curricula.**

>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```
>**This JWT would be a trainer's.**

> **Response Body:**
>
>```json
>[
>    {
>        "id": 1,
>        "name": "BIOS",
>        "trainer": {
>            "id": 1,
>            "username": "biosman",
>            "firstName": "Gary",
>            "lastName": "Kildall",
>            "topics": [],
>            "curricula": [],
>            "modules": [],
>            "trainer": true
>        },
>        "events": [],
>        "revUsers": [
>            {
>                "id": 2,
>                "username": "billyg",
>                "firstName": "Bill",
>                "lastName": "Gates",
>                "topics": [],
>                "modules": [],
>                "ownedCurricula": [],
>                "trainer": false
>            },
>            {
>                "id": 3,
>                "username": "dosman",
>                "firstName": "Tim",
>                "lastName": "Paterson",
>                "topics": [],
>                "modules": [],
>                "ownedCurricula": [],
>                "trainer": false
>            }
>        ]
>    }
>]



### ```/curriculum/assigned```

- ### ```GET``` - **Get all curriculum assigned to whoever sends the request. Associates are assigned to curricula.**
>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```
>**This JWT would be an associate's.**

>**Response Body:**
>
>```json
>[
>    {
>        "id": 1,
>        "name": "BIOS",
>        "trainer": {
>            "id": 1,
>            "username": "biosman",
>            "firstName": "Gary",
>            "lastName": "Kildall",
>            "topics": [],
>            "curricula": [],
>            "modules": [],
>            "trainer": true
>        },
>        "events": [],
>        "revUsers": [
>            {
>                "id": 2,
>                "username": "billyg",
>                "firstName": "Bill",
>                "lastName": "Gates",
>                "topics": [],
>                "modules": [],
>                "ownedCurricula": [],
>                "trainer": false
>            },
>            {
>                "id": 3,
>                "username": "dosman",
>                "firstName": "Tim",
>                "lastName": "Paterson",
>                "topics": [],
>                "modules": [],
>                "ownedCurricula": [],
>                "trainer": false
>            }
>        ]
>    }
>]
---

## ```/technology_category```
- ### ```POST``` - **Create a new technology category.**
>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```

> **Request Body:**
>```json
>{
>    "name": "HDD"
>}
>```

> **Response Body:**
>```json
>{
>    "id": 1,
>    "name": "HDD"
>}

- ### ```GET``` - **Read all available technology categories, probably by a trainer when creating topics.**
>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```

> **Response Body:**
>```json
>[
>    {
>        "id": 1,
>        "name": "HDD"
>    },
>    {
>        "id": 2,        
>        "name": "CPU"
>    }
>]
---

## ```/module```

- ### ```POST``` - **Create a new module.**
>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```

> **Request Body:**
>```json
>{
>    "name": "Boot",
>    "technologyCategory": 1,
>    "description": "The boot process of a BIOS."
>}
>```
>>`technologyCategory` **the id of the technology category (which should already exist) associated with the module.**

> **Response Body:**
>```json
>{
>    "id": 1,
>    "name": "Boot",
>    "description": "The boot process of a BIOS.",
>    "trainer": {
>        "id": 1,
>        "username": "biosman",
>        "firstName": "Gary",
>        "lastName": "Kildall",
>        "topics": [],
>        "curricula": [],
>        "modules": [
>            {
>                "id": 1,
>                "name": "Boot",
>                "description": "The boot process of a BIOS.",
>                "technologyCategory": {
>                    "id": 1,
>                    "name": null
>                }
>            }
>        ],
>        "ownedCurricula": [
>            {
>                "id": 1,
>                "name": "BIOS",
>                "events": [],
>                "revUsers": [
>                    {
>                        "id": 2,
>                        "username": "billyg",
>                        "firstName": "Bill",
>                        "lastName": "Gates",
>                        "topics": [],
>                        "modules": [],
>                        "ownedCurricula": [],
>                        "trainer": false
>                    },
>                    {
>                        "id": 3,
>                        "username": "dosman",
>                        "firstName": "Tim",
>                        "lastName": "Paterson",
>                        "topics": [],
>                        "modules": [],
>                        "ownedCurricula": [],
>                        "trainer": false
>                    }
>                ]
>            }
>        ],
>        "trainer": true
>    },
>    "technologyCategory": {
>        "id": 1,
>        "name": null
>    },
>    "topics": []
>}
>```

- ### ```GET``` - **Read all available modules, probably by a trainer when creating topics.**

>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```

> **Response Body:**
>```json
>[
>    {
>        "id": 1,
>        "name": "Boot",
>        "description": "The boot process of a BIOS.",
>        "trainer": {
>            "id": 1,
>            "username": "biosman",
>            "firstName": "Gary",
>            "lastName": "Kildall",
>            "topics": [],
>            "curricula": [],
>            "modules": [
>                {
>                    "id": 1,
>                    "name": "Boot",
>                    "description": "The boot process of a BIOS.",
>                    "technologyCategory": {
>                        "id": 1,
>                        "name": "HDD"
>                    }
>                }
>            ],
>            "ownedCurricula": [
>                {
>                    "id": 1,
>                    "name": "BIOS",
>                    "events": [],
>                    "revUsers": [
>                        {
>                            "id": 2,
>                            "username": "billyg",
>                            "firstName": "Bill",
>                            "lastName": "Gates",
>                            "topics": [],
>                            "modules": [],
>                            "ownedCurricula": [],
>                            "trainer": false
>                        },
>                        {
>                            "id": 3,
>                            "username": "dosman",
>                            "firstName": "Tim",
>                            "lastName": "Paterson",
>                            "topics": [],
>                            "modules": [],
>                            "ownedCurricula": [],
>                            "trainer": false
>                        }
>                    ]
>                }
>            ],
>            "trainer": true
>        },
>        "technologyCategory": {
>            "id": 1,
>            "name": "HDD"
>        },
>        "topics": []
>    }
>]
>```

- ### ```PUT``` - **Update an existing module.**

>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```

> **Request Body:**
>```json
>{
>    "id": 1,
>    "name": "Boot Operation",
>    "technologyCategory": 1,
>    "description": "The process upon startup."
>}
>```
>> ___All fields are required with PUT request, otherwise they will become `null`.___
>> 
>> `technologyCategory` **the id of the technologyCategory associated with this module.**

> **Response Body:**
>```json
>[
>    {
>        "id": 1,
>        "name": "Boot Operation",
>        "description": "The process upon startup.",
>        "trainer": {
>            "id": 1,
>            "username": "biosman",
>            "firstName": "Gary",
>            "lastName": "Kildall",
>            "topics": [],
>            "curricula": [],
>            "modules": [
>                {
>                    "id": 1,
>                    "name": "Boot",
>                    "description": "The boot process of a BIOS.",
>                    "technologyCategory": {
>                        "id": 1,
>                        "name": "HDD"
>                    }
>                }
>            ],
>            "ownedCurricula": [
>                {
>                    "id": 1,
>                    "name": "BIOS",
>                    "events": [],
>                    "revUsers": [
>                        {
>                            "id": 2,
>                            "username": "billyg",
>                            "firstName": "Bill",
>                            "lastName": "Gates",
>                            "topics": [],
>                            "modules": [],
>                            "ownedCurricula": [],
>                            "trainer": false
>                        },
>                        {
>                            "id": 3,
>                            "username": "dosman",
>                            "firstName": "Tim",
>                            "lastName": "Paterson",
>                            "topics": [],
>                            "modules": [],
>                            "ownedCurricula": [],
>                            "trainer": false
>                        }
>                    ]
>                }
>            ],
>            "trainer": true
>        },
>        "technologyCategory": {
>            "id": 1,
>            "name": "HDD"
>        },
>        "topics": []
>    }
>]
>```

- ### ```DELETE module/{id}``` - **Delete an existing module.**
> **`id` of the module to delete goes in the Request URI path.**
> 
> `module/1`

>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```

>**Response Code**
>
> **200 - no matter what**
---


## ```/topic```

- ### ```POST``` - **Create a new topic.**
>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```

> **Request Body:**
>```json
>{
>    "title":"Testing all hardware",
>    "description": "Covers all the aspects of the first step of the boot process.",
>    "estimatedDuration": 600,
>    "lectureNotes": "There are probably volumes written about this",
>    "githubRepo": "http://.com",
>    "trainer": 1,
>    "technologyCategory": 1,
>    "modules": []
>}
>```
>>`trainer` **the id of the trainer making the topic.**
>>
>>`technologyCategory` **the id of the technology category (which should already exist) associated with the topic.**
>>
>> `modules` **array of module ids that this topic can be associated with, can be an empty array, or not included in the request.**

>**Response Body**
>```json
>{
>    "id": 1,
>    "title": "Testing all hardware",
>    "description": "Covers all the aspects of the first step of the boot process.",
>    "estimatedDuration": 600,
>    "lectureNotes": "There are probably volumes written about this",
>    "githubRepo": "http://.com",
>    "trainer": {
>        "id": 1,
>        "username": "biosman",
>        "firstName": "Gary",
>        "lastName": "Kildall",
>        "topics": [
>            {
>                "id": 1,
>                "title": "Testing all hardware",
>                "description": "Covers all the aspects of the first step of the boot process.",
>                "estimatedDuration": 600,
>                "lectureNotes": "There are probably volumes written about this",
>                "githubRepo": "http://.com",
>                "technologyCategory": {
>                    "id": 1,
>                    "name": null
>                },
>                "modules": []
>            }
>        ],
>        "curricula": [],
>        "modules": [],
>        "ownedCurricula": [
>            {
>                "id": 1,
>                "name": "BIOS",
>                "events": [],
>                "revUsers": [
>                    {
>                        "id": 2,
>                        "username": "billyg",
>                        "firstName": "Bill",
>                        "lastName": "Gates",
>                        "topics": [],
>                        "modules": [],
>                        "ownedCurricula": [],
>                        "trainer": false
>                    },
>                    {
>                        "id": 3,
>                        "username": "dosman",
>                        "firstName": "Tim",
>                        "lastName": "Paterson",
>                        "topics": [],
>                        "modules": [],
>                        "ownedCurricula": [],
>                        "trainer": false
>                    }
>                ]
>            }
>        ],
>        "trainer": true
>    },
>    "technologyCategory": {
>        "id": 1,
>        "name": null
>    },
>    "modules": []
>}
>```

- ### ```PUT``` - **Update an existing topic.**
>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```

> **Request Body:**
>```json
>{
>    "id": 1, 
>    "title":"Testing all hardware",
>    "description": "Covers all the aspects of the first step of the boot process.",
>    "estimatedDuration": 600,
>    "lectureNotes": "There are probably volumes written about this",
>    "githubRepo": "http://.com",
>    "trainer": 1,
>    "technologyCategory": 1,
>    "modules": [2]
>}
>```
>> ___All fields are required with PUT request, otherwise they will become `null`.___
>> 
>>`trainer` **the id of the trainer making the topic.**
>> 
>>`technologyCategory` **the id of the technology category (which should already exist) associated with the topic.**
>> 
>> `modules` **array of ids of modules that this topic belongs to.**

> **Response Body:**
>```json
>{
>    "id": 1,
>    "title": "Testing all hardware",
>    "description": "Covers all the aspects of the first step of the boot process.",
>    "estimatedDuration": 600,
>    "lectureNotes": "There are probably volumes written about this",
>    "githubRepo": "http://.com",
>    "trainer": {
>        "id": 1,
>        "username": "biosman",
>        "firstName": "Gary",
>        "lastName": "Kildall",
>        "topics": [
>            {
>                "id": 1,
>                "title": "Testing all hardware",
>                "description": "Covers all the aspects of the first step of the boot process.",
>                "estimatedDuration": 600,
>                "lectureNotes": "There are probably volumes written about this",
>                "githubRepo": "http://.com",
>                "technologyCategory": {
>                    "id": 1,
>                    "name": "HDD"
>                },
>                "modules": [
>                    {
>                        "id": 2,
>                        "name": "Boot",
>                        "description": "The boot process of a BIOS.",
>                        "technologyCategory": {
>                            "id": 1,
>                            "name": "HDD"
>                        }
>                    }
>                ]
>            }
>        ],
>        "curricula": [],
>        "modules": [
>            {
>                "id": 2,
>                "name": "Boot",
>                "description": "The boot process of a BIOS.",
>                "technologyCategory": {
>                    "id": 1,
>                    "name": "HDD"
>                }
>            },
>            {
>                "id": 3,
>                "name": "Boot",
>                "description": "The boot process of a BIOS.",
>                "technologyCategory": {
>                    "id": 1,
>                    "name": "HDD"
>                }
>            }
>        ],
>        "ownedCurricula": [
>            {
>                "id": 1,
>                "name": "BIOS",
>                "events": [],
>                "revUsers": [
>                    {
>                        "id": 2,
>                        "username": "billyg",
>                        "firstName": "Bill",
>                        "lastName": "Gates",
>                        "topics": [],
>                        "modules": [],
>                        "ownedCurricula": [],
>                        "trainer": false
>                    },
>                    {
>                        "id": 3,
>                        "username": "dosman",
>                        "firstName": "Tim",
>                        "lastName": "Paterson",
>                        "topics": [],
>                        "modules": [],
>                        "ownedCurricula": [],
>                        "trainer": false
>                    }
>                ]
>            }
>        ],
>        "trainer": true
>    },
>    "technologyCategory": {
>        "id": 1,
>        "name": "HDD"
>    },
>    "modules": [
>        {
>            "id": 2,
>            "name": "Boot",
>            "description": "The boot process of a BIOS.",
>            "technologyCategory": {
>                "id": 1,
>                "name": "HDD"
>            }
>        }
>    ]
>}
>```


- ### ```GET``` - **Read all topics owned by the trainer making the request.**

>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```
>
> **Response Body:**
>```
>[
> {... an entire topic object (as seen above)},
> {... another topic object}
>]
>```
>

- ### ```GET topic/all``` - **Read every topic by all trainers.**
>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```
> **Response Body:**
>```
>[
> {... an entire topic object (as seen above)},
> {... another topic object}
>]
>```


- ### ```GET topic/{id}``` - **Read one topic.**
> **`id` of the topic to read goes in the Request URI path.**
>
> `topic/1`

>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```

> **Response Body:**
>```json
>{
>    "id": 1,
>    "title": "Testing all hardware",
>    "description": "Covers all the aspects of the first step of the boot process.",
>    "estimatedDuration": 600,
>    "lectureNotes": "There are probably volumes written about this",
>    "githubRepo": "http://.com",
>    "trainer": {
>        "id": 1,
>        "username": "biosman",
>        "firstName": "Gary",
>        "lastName": "Kildall",
>        "topics": [
>            {
>                "id": 1,
>                "title": "Testing all hardware",
>                "description": "Covers all the aspects of the first step of the boot process.",
>                "estimatedDuration": 600,
>                "lectureNotes": "There are probably volumes written about this",
>                "githubRepo": "http://.com",
>                "technologyCategory": {
>                    "id": 1,
>                    "name": "HDD"
>                },
>                "modules": [
>                    {
>                        "id": 2,
>                        "name": "Boot",
>                        "description": "The boot process of a BIOS.",
>                        "technologyCategory": {
>                            "id": 1,
>                            "name": "HDD"
>                        }
>                    }
>                ]
>            }
>        ],
>        "curricula": [],
>        "modules": [
>            {
>                "id": 2,
>                "name": "Boot",
>                "description": "The boot process of a BIOS.",
>                "technologyCategory": {
>                    "id": 1,
>                    "name": "HDD"
>                }
>            },
>            {
>                "id": 3,
>                "name": "Boot",
>                "description": "The boot process of a BIOS.",
>                "technologyCategory": {
>                    "id": 1,
>                    "name": "HDD"
>                }
>            }
>        ],
>        "ownedCurricula": [
>            {
>                "id": 1,
>                "name": "BIOS",
>                "events": [],
>                "revUsers": [
>                    {
>                        "id": 2,
>                        "username": "billyg",
>                        "firstName": "Bill",
>                        "lastName": "Gates",
>                        "topics": [],
>                        "modules": [],
>                        "ownedCurricula": [],
>                        "trainer": false
>                    },
>                    {
>                        "id": 3,
>                        "username": "dosman",
>                        "firstName": "Tim",
>                        "lastName": "Paterson",
>                        "topics": [],
>                        "modules": [],
>                        "ownedCurricula": [],
>                        "trainer": false
>                    }
>                ]
>            }
>        ],
>        "trainer": true
>    },
>    "technologyCategory": {
>        "id": 1,
>        "name": "HDD"
>    },
>    "modules": [
>        {
>            "id": 2,
>            "name": "Boot",
>            "description": "The boot process of a BIOS.",
>            "technologyCategory": {
>                "id": 1,
>                "name": "HDD"
>            }
>        }
>    ]
>}
>```

- ### ```GET topic/module/{id}``` - **Read all topics belonging to a module.**
> **`id` of the module goes in the Request URI path.**
>
> `topic/module/1`

>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```
> **Response Body:**
>```
>[
> {... an entire topic object (as seen above)},
> {... another topic object}
>]
>```
>
---
## ```/event```

- ### ```POST``` - **Create a calendar event for a given topic within a given curriculum.**
>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```
> **Request Body:**
>
>```json
>{
>    "startDatetime": 1633111200,
>    "curriculum": 1,
>    "topic":1
>}
>```
>>`startDatetime` **The unix timestamp of when the event starts.**
>> 
>>`curriculum` **the id of the curriculum associated with the topic.**
>>
>> `topic` **The id of the topic.**

>**Response Body:**
>```json
>{
>    "id": 1,
>    "curriculum": {
>        "id": 1,
>        "name": null,
>        "trainer": null
>    },
>    "startDatetime": 1633111200,
>    "topic": {
>        "id": 1,
>        "title": null,
>        "description": null,
>        "estimatedDuration": 0,
>        "lectureNotes": null,
>        "githubRepo": null,
>        "technologyCategory": null,
>        "modules": null
>    }
>}
>```

- ### ```GET event/{curriculum id}``` - **Read all events associated with one curriculum.**
> **`id` of the curriculum goes in the Request URI path.**
>
> `event/1`

>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```

> **Response Body:**
> ```json
> [
>    {
>        "id": 1,
>        "curriculum": {
>            "id": 1,
>            "name": "BIOS",
>            "trainer": {
>                "id": 1,
>                "username": "biosman",
>                "firstName": "Gary",
>                "lastName": "Kildall",
>                "topics": [
>                    {
>                        "id": 1,
>                        "title": "Testing all hardware",
>                        "description": "Covers all the aspects of the first step of the boot process.",
>                        "estimatedDuration": 600,
>                        "lectureNotes": "There are probably volumes written about this",
>                        "githubRepo": "http://.com",
>                        "technologyCategory": {
>                            "id": 1,
>                            "name": "HDD"
>                        },
>                        "modules": [
>                            {
>                                "id": 2,
>                                "name": "Boot",
>                                "description": "The boot process of a BIOS.",
>                                "technologyCategory": {
>                                    "id": 1,
>                                    "name": "HDD"
>                                }
>                            }
>                        ]
>                    }
>                ],
>                "curricula": [],
>                "modules": [
>                    {
>                        "id": 2,
>                        "name": "Boot",
>                        "description": "The boot process of a BIOS.",
>                        "technologyCategory": {
>                            "id": 1,
>                            "name": "HDD"
>                        }
>                    },
>                    {
>                        "id": 3,
>                        "name": "Boot",
>                        "description": "The boot process of a BIOS.",
>                        "technologyCategory": {
>                            "id": 1,
>                            "name": "HDD"
>                        }
>                    }
>                ],
>                "trainer": true
>            },
>            "hibernateLazyInitializer": {}
>        },
>        "startDatetime": 1633111200,
>        "topic": {
>            "id": 1,
>            "title": "Testing all hardware",
>            "description": "Covers all the aspects of the first step of the boot process.",
>            "estimatedDuration": 600,
>            "lectureNotes": "There are probably volumes written about this",
>            "githubRepo": "http://.com",
>            "technologyCategory": {
>                "id": 1,
>                "name": "HDD"
>            },
>            "modules": [
>                {
>                    "id": 2,
>                    "name": "Boot",
>                    "description": "The boot process of a BIOS.",
>                    "technologyCategory": {
>                        "id": 1,
>                        "name": "HDD"
>                    }
>                }
>            ]
>        }
>    }
> ]
>```

- ### ```PUT``` - **Update an existing event.**
>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```

> **Request Body:**
>```json
>{
>    "id": 1, 
>    "curriculum": 1,
>    "startDatetime": 1633114800,
>    "topic": 1
>}
>```
>> ___All fields are required with PUT request, otherwise they will become `null`.___
>>
>>`curriculum` **the id of the curriculum associated with the topic.**
>>
>>`startDatetime` **The unix timestamp of when the event starts.**
>>
>> `topic` **The id of the topic.**

> **Response Body:**
> ```json
> [
>    {
>        "id": 1,
>        "curriculum": {
>            "id": 1,
>            "name": "BIOS",
>            "trainer": {
>                "id": 1,
>                "username": "biosman",
>                "firstName": "Gary",
>                "lastName": "Kildall",
>                "topics": [
>                    {
>                        "id": 1,
>                        "title": "Testing all hardware",
>                        "description": "Covers all the aspects of the first step of the boot process.",
>                        "estimatedDuration": 600,
>                        "lectureNotes": "There are probably volumes written about this",
>                        "githubRepo": "http://.com",
>                        "technologyCategory": {
>                            "id": 1,
>                            "name": "HDD"
>                        },
>                        "modules": [
>                            {
>                                "id": 2,
>                                "name": "Boot",
>                                "description": "The boot process of a BIOS.",
>                                "technologyCategory": {
>                                    "id": 1,
>                                    "name": "HDD"
>                                }
>                            }
>                        ]
>                    }
>                ],
>                "curricula": [],
>                "modules": [
>                    {
>                        "id": 2,
>                        "name": "Boot",
>                        "description": "The boot process of a BIOS.",
>                        "technologyCategory": {
>                            "id": 1,
>                            "name": "HDD"
>                        }
>                    },
>                    {
>                        "id": 3,
>                        "name": "Boot",
>                        "description": "The boot process of a BIOS.",
>                        "technologyCategory": {
>                            "id": 1,
>                            "name": "HDD"
>                        }
>                    }
>                ],
>                "trainer": true
>            },
>            "hibernateLazyInitializer": {}
>        },
>        "startDatetime": 1633114800,
>        "topic": {
>            "id": 1,
>            "title": "Testing all hardware",
>            "description": "Covers all the aspects of the first step of the boot process.",
>            "estimatedDuration": 600,
>            "lectureNotes": "There are probably volumes written about this",
>            "githubRepo": "http://.com",
>            "technologyCategory": {
>                "id": 1,
>                "name": "HDD"
>            },
>            "modules": [
>                {
>                    "id": 2,
>                    "name": "Boot",
>                    "description": "The boot process of a BIOS.",
>                    "technologyCategory": {
>                        "id": 1,
>                        "name": "HDD"
>                    }
>                }
>            ]
>        }
>    }
> ]
>```

 - ### ```DELETE event/{id}``` - **Delete an existing event.**
> **`id` of the event to delete goes in the Request URI path.**
>
> `event/1`

>**Request Header:**
>```json
>{"Authorization": "Bearer ####.####.####"}
>```

>**Response Code**
>
> **200 - no matter what**