import tkinter as tk
from tkinter import messagebox
import datetime
import mysql.connector

class ProjeZamanTakipUygulamasi:
    def __init__(self, master):
        self.master = master
        master.title("Project Time Tracking Application")

        self.proje = None
        self.proje_adi = ""

        self.label = tk.Label(master, text="Project Time Tracking Application")
        self.label.pack()

        self.proje_adi_label = tk.Label(master, text="Project name:")
        self.proje_adi_label.pack()

        self.proje_adi_entry = tk.Entry(master)
        self.proje_adi_entry.pack()

        self.baslat_button = tk.Button(master, text="Start a Project", command=self.proje_baslat)
        self.baslat_button.pack()

        self.bitir_button = tk.Button(master, text="Finish Project", command=self.proje_bitir, state=tk.DISABLED)
        self.bitir_button.pack()

        # MySQL Connection
        self.mydb = mysql.connector.connect(
            host="localhost",
            user="root",
            password="",
            database="proje"
        )
        self.mycursor = self.mydb.cursor()

    def proje_baslat(self):
        self.proje_adi = self.proje_adi_entry.get()
        if self.proje_adi:
            self.proje = Proje(self.proje_adi)
            self.proje.baslat()
            self.baslat_button.config(state=tk.DISABLED)
            self.bitir_button.config(state=tk.NORMAL)
        else:
            messagebox.showwarning("Warning", "Please enter a project name.")

    def proje_bitir(self):
        self.proje.bitir()
        self.baslat_button.config(state=tk.NORMAL)
        self.bitir_button.config(state=tk.DISABLED)
        self.kaydet()

    def kaydet(self):
        now = datetime.datetime.now()
        sure = self.proje.sure_hesapla()
        sql = "INSERT INTO sureler (proje_adi, baslangic_zamani, bitis_zamani, sure) VALUES (%s, %s, %s, %s)"
        val = (self.proje.ad, self.proje.baslama_zamani, self.proje.bitis_zamani, sure)
        self.mycursor.execute(sql, val)
        self.mydb.commit()
        print(f"{self.proje.ad} projesi kaydedildi.")

class Proje:
    def __init__(self, ad):
        self.ad = ad
        self.baslama_zamani = datetime.datetime.now()
        self.bitis_zamani = None

    def baslat(self):
        messagebox.showinfo("Information", f"{self.ad} project started.")

    def bitir(self):
        self.bitis_zamani = datetime.datetime.now()
        sure = self.sure_hesapla()
        messagebox.showinfo("Information", f"{self.ad} project is finished. Total time: {sure}")

    def sure_hesapla(self):
        if self.bitis_zamani is not None:
            sure = self.bitis_zamani - self.baslama_zamani
            return sure
        else:
            return "The project is not finished yet."

root = tk.Tk()
uygulama = ProjeZamanTakipUygulamasi(root)
root.mainloop()
