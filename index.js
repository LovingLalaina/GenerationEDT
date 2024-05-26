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
    console.log("connected to DB");
  } catch (err) {
    throw err;
  }
};

const corsOptions = {
  origin: ["http://localhost:3000", "https://front-gestion-edt.vercel.app"],
};

app.use(cors(corsOptions));
app.use(express.json());
app.use(morgan("dev"));
app.use(express.urlencoded({ extended: true }));

app.use("/api", edtRouter);
app.use("/api", generateRouter);

app.listen(5000, () => {
  connect();
  console.log(`API deploye`);
});
