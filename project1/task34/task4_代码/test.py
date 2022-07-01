import psycopg2
import pandas as pd
import time
import warnings

warnings.filterwarnings("ignore")
con = None


def connect_select():
    global con
    try:
        con = psycopg2.connect(database="contract_project",
                               user="checker",
                               password="123456",
                               host="localhost",
                               port=5432)
        print("connection success")
        cur = con.cursor()
        start_time = time.time()
        for i in range(50):
            cur.execute(" select from order_sale where product_model= 'Mp437' ")
        con.commit()
        end_time = time.time()
        time_lapsed = end_time - start_time
        print("DBMS " + str(time_lapsed * 1000) + " ms")
        con.close()
    except psycopg2.DatabaseError:
        if con:
            con.rollback()
        # print("connection failed")


def connect_update():
    global con
    try:
        con = psycopg2.connect(database="contract_project",
                               user="checker",
                               password="123456",
                               host="localhost",
                               port=5432)
        print("connection success")
        cur = con.cursor()
        start_time = time.time()
        for i in range(100):
            cur.execute("update order_sale set quantity = 5000 where order_id = 1")
            con.commit()
        end_time = time.time()
        time_lapsed = end_time - start_time
        print("DBMS " + str(time_lapsed * 1000) + " ms")
        con.close()
    except psycopg2.DatabaseError:
        if con:
            con.rollback()
        # print("connection failed")


def connect_delete():
    global con
    try:
        con = psycopg2.connect(database="contract_project",
                               user="checker",
                               password="123456",
                               host="localhost",
                               port=5432)
        print("connection success")
        cur = con.cursor()
        start_time = time.time()

        cur.execute("delete from order_sale where order_id= 2")
        con.commit()
        end_time = time.time()
        time_lapsed = end_time - start_time
        print("DBMS " + str(time_lapsed * 1000) + " ms")
        con.close()
    except psycopg2.DatabaseError:
        if con:
            con.rollback()
        # print("connection failed")


def connect_insert():
    global con
    try:
        con = psycopg2.connect(database="contract_project",
                               user="checker",
                               password="123456",
                               host="localhost",
                               port=5432)
        print("connection success")
        cur = con.cursor()
        f_r = open("insertData(1).csv", "r")
        start_time = time.time()
        for i in f_r:
            arr = i.split(",")
            cur.execute(
                """insert into order_sale (order_id, contract_number, product_model,est_date,lodge_date,quantity,salesman_id) values (%s,%s,%s,%s,%s,%s,%s);""",
                (int(arr[0]), arr[1], arr[2], arr[3], arr[4], int(arr[5]), int(arr[6])))
        con.commit()
        end_time = time.time()
        time_lapsed = end_time - start_time
        print("DBMS " + str(time_lapsed * 1000) + " ms")
        con.close()
    except psycopg2.DatabaseError:
        if con:
            print(psycopg2.DatabaseError)
            con.rollback()
        # print("connection failed")


def file_select(model):
    f = open("order.csv", "r")
    for i in f:
        add = False
        arr = i.split(",")
        for j in arr:
            if arr[2] == model:
                add = True
                break
        if add is True:
            continue
            print(
                str(arr[1]) + " " + str(arr[2]) + " " + str(arr[3]) + " " + str(arr[4]) + " " + str(arr[5]) + " " + str(
                    arr[6]))


def file_insert():
    start = time.time()
    f_r = open("insertData(1).csv", "r")
    f_w = open("order.csv", "a")
    for i in f_r:
        f_w.write(i)
    end = time.time()
    print("File IO " + str((end - start)*1000) + " ms")


def file_update(id, quantity):
    f = open("order.csv", "r")
    wr = []
    for i in f:
        arr = i.split(",")
        if arr[0] == str(id):
            arr[5] = str(quantity)
        s = ","
        s = s.join(arr)
        wr.append(s)
    f.close()
    f_w = open("order.csv", "w")
    f_w.truncate()
    for i in wr:
        f_w.write(str(i))


def file_delete(id):
    f_r = open("order.csv", "r")
    wr = []
    for i in f_r:
        arr = i.split(",")
        if arr[0] != str(id):
            wr.append(i)

    f_r.close()
    f_w = open("order.csv", "w")
    f_w.truncate()
    for i in wr:
        f_w.write(str(i))


def p_file_select(model):
    df = pd.read_csv("order.csv", ',', None, None)
    df = df[(df[2] == model)]
    # print(df)


def p_file_update(id, quantity):
    df = pd.read_csv("order.csv", ',', None, None)
    for ind in df.index:
        if df[0][ind] == id:
            df[5][ind] = quantity
            break
    df.to_csv(path_or_buf="order.csv", header=False, index=False)


def p_file_delete(id):
    df = pd.read_csv("order.csv", ',', None, None)
    df = df.drop(id - 1)
    df.to_csv(path_or_buf="order.csv", header=False, index=False)


def p_file_insert():
    start = time.time()
    df2 = pd.read_csv("insertData(1).csv", ',', None, None)
    df = pd.read_csv("order.csv", ',', None, None)
    df = pd.concat([df, df2])
    df.reset_index()
    df.to_csv(path_or_buf="order.csv", header=False, index=False)
    end = time.time()
    print(str((end - start)*1000) + " ms")



# f_start = time.time()
# for i in range(100):
#     file_delete(i+1)
# f_end = time.time()
# print("File IO " + str((f_end - f_start)*1000) + " ms")

connect_insert()
for i in range(100):
    p_file_update(1,5000)


# ref: https://www.psycopg.org/docs/
# https://pandas.pydata.org/docs/user_guide/io.html#sql-queries
