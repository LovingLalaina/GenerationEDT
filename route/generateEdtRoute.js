import express, { Router } from "express";
import {
  generateTimetable,
  getAllGenere,
} from "../controller/generateEdtController.js";

const generateRouter = express.Router();

generateRouter.post("/generate", generateTimetable);
generateRouter.get("/generate", getAllGenere);

export default generateRouter;
