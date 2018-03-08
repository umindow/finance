[{
    "name": "用户管理", 
    "url": "#",
    "icon": "fa-github-alt",
    "level": 20,
    "checkDep":false,
    "sub_menus": [
       {
         "name": "用户列表", 
          "url": "/user/list",
          "icon": "fa-user",
          "level":  20,
          "checkDep":false
          }
       ]
},
{
    "name": "配置管理",
    "url": "#",
    "icon": "fa-github-alt",
    "level": 20,
    "checkDep":false,
    "sub_menus": [
       {
         "name": "配置列表",
          "url": "/config/list",
          "icon": "fa-user",
          "level":  20,
          "checkDep":false
          }
       ]
},
{
    "name": "数据管理", 
    "url": "#",
    "icon": "fa-database",
    "level": 10,
    "checkDep":true,
     "sub_menus": [
           {
             "name": "银行授信数据",
              "url": "/data/genericDataList?dataType=200",
              "icon": "fa-user",
              "level":  10,
              "checkDep":true
              },
             {"name": "系统业务数据",
              "url": "/data/genericDataList?dataType=100",
              "icon": "fa-user",
              "level":  10,
              "checkDep":true
              },
             {"name": "还款数据",
              "url": "/data/genericDataList?dataType=300",
              "icon": "fa-user",
              "level":  10,
              "checkDep":true
              },
             {"name": "收费退费数据",
              "url": "/data/genericDataList?dataType=400",
              "icon": "fa-user",
              "level":  10,
              "checkDep":true
              },
             {"name": "代偿数据",
              "url": "/data/genericDataList?dataType=500",
              "icon": "fa-user",
              "level":  10,
              "checkDep":true
              },
             {"name": "追偿数据",
              "url": "/data/genericDataList?dataType=600",
              "icon": "fa-user",
              "level":  10,
              "checkDep":true
              }                                                                      
           ]
}
]

