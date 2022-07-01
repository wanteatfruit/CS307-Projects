import time
import os
import pandas as pd
import sqlalchemy as al
from psycopg2 import extras
import psycopg2
from sqlalchemy import create_engine

con = None


def using_alchemy():
    start = time.time()
    col = ["order_id", "contract_number", "product_model", "est_date", "lodge_date", "quantity", "salesman_id"]
    df = pd.read_csv("order.csv", names=col)
    eg = create_engine('postgresql://checker:123456@localhost:5432/contract_project')
    df.to_sql(name='order_sale', con=eg, index=False, if_exists='replace')
    end = time.time()
    print(str((end - start)*1000) + " ms")


def using_raw_execute():
    start = time.time()
    global con
    try:
        con = psycopg2.connect(database="contract_project",
                               user="checker",
                               password="123456",
                               host="localhost",
                               port=5432)
        print("connection success")
        cur = con.cursor()
        f_r = open("order.csv", "r")
        start_time = time.time()
        for i in f_r:
            arr = i.split(",")
            if arr[4] == '':
                arr[4] = None
            cur.execute(
                """insert into order_sale (order_id, contract_number, product_model,est_date,lodge_date,quantity,salesman_id) values (%s::int,%s,%s,%s::date,%s::date,%s::int,%s::int);""",
                (arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6]))
        con.commit()
        end_time = time.time()
        time_lapsed = end_time - start_time
        con.close()
        end = time.time()
        print(str((end - start) * 1000) + " ms")
    except psycopg2.DatabaseError as e:
        if con:
            print(e)
            con.rollback()
        # print("connection failed")


def using_execute_batch():
    start = time.time()
    global con
    try:
        con = psycopg2.connect(database="contract_project",
                               user="checker",
                               password="123456",
                               host="localhost",
                               port=5432)
        print("connection success")
        arg_list = []
        f_r = open("order.csv", "r")
        for i in f_r:
            arr = i.split(",")
            list1 = []
            for j in arr:
                if j == "":
                    j = None
                list1.append(j)
            arg_list.append(tuple(list1))
        cur = con.cursor()
        stmt = """insert into order_sale (order_id, contract_number, product_model,est_date,lodge_date,quantity,salesman_id) values (%s::int,%s,%s,%s::date,%s::date,%s::int,%s::int) """
        extras.execute_batch(cur, stmt, arg_list)
        con.commit()
    except psycopg2.DatabaseError as e:
        if con:
            print(e)
            con.rollback()
    end = time.time()
    print(str((end - start) * 1000) + " ms")


#
using_alchemy()
#using_raw_execute()
#using_execute_batch()

# https://www.tutorialspoint.com/sqlalchemy/sqlalchemy_core_connecting_to_database.htm
# https://www.psycopg.org/docs/extras.html#fast-exec
