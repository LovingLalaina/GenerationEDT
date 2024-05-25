import express from "express";
import mongoose from "mongoose";
import morgan from "morgan";
import cors from "cors";
import edtRouter from "./route/edtRoute.js";
import generateRouter from "./route/generateEdtRoute.js";

const app = express();

const connect = async () => {
  try {
    await mongoose.connect("mongodb+srv://lovinglalainaa:s6QZVUXEXQgvlvhp@cluster0.zauooiv.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0/generate-edt");
    console.log("API connected to DB");
  } catch (err) {
    throw err;
  }
};

app.use(cors());
app.use(express.json());
app.use(morgan("dev"));
app.use(express.urlencoded({ extended: true }));

app.use("/api", edtRouter);
app.use("/api", generateRouter);

app.listen(5000, () => {
  connect();
  console.log(`API deploye`);
});
