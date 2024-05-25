import express, { Router } from "express";

import {
  addEdt,
  deleteEdt,
  editEdt,
  edtById,
  getAllEdt,
} from "../controller/edtController.js";

const edtRouter = express.Router();

edtRouter.post("/edt", addEdt);
edtRouter.delete("/edt/:id", deleteEdt);
edtRouter.get("/edt", getAllEdt);
edtRouter.put("/edt/:id", editEdt);
edtRouter.get("/edt/:id", edtById);
export default edtRouter;
