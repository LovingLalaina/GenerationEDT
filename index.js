import express from "express";
import mongoose from "mongoose";
import morgan from "morgan";
import cors from "cors";
import edtRouter from "./route/edtRoute.js";
import generateRouter from "./route/generateEdtRoute.js";

const app = express();

const connect = async () => {
  try {
    await mongoose.connect("mongodb://127.0.0.1:27017/generate-edt");
    console.log("generation connected");
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
  console.log(`server connect at https://localhost:5000}`);
});
