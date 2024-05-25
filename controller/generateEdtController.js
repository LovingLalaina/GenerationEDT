import Prof from "../model/professeur.js";
import Matiere from "../model/matiere.js";
import Niveau from "../model/niveau.js";
import Edt from "../model/edt.js";
import Classe from "../model/classe.js";
import GenerateEdt from "../model/generateEdt.js";

// Function to generate a timetable
export const generateTimetable = async (req, res) => {
  try {
    // Fetch data from models
    const profs = await Prof.find();
    const matieres = await Matiere.find();
    const niveaux = await Niveau.find();
    const classes = await Classe.find();
    const cours = await Edt.find();

    // Your timetable generation logic goes here
    const timetable = generateTimetableLogic(profs, matieres, niveaux, classes , cours);

    // Save the generated timetable to the database (assuming Edt is your timetable model)
    const saveToBase = await Promise.all(
      timetable.map((elem) => {
        const saveGenerate = new GenerateEdt({
          matiere: elem.matiere,
          heure: elem.heure, // Replace with your logic for generating time
          date: elem.date, // Replace with your logic for generating date
          prof: elem.prof,
          niveau: elem.niveau,
          classe: elem.classe,
        });
        return saveGenerate.save();
      })
    );

    res.status(200).json({ success: true, saveToBase });
  } catch (error) {
    console.error("Error generating timetable:", error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};

// Placeholder for your actual timetable generation logic
const generateTimetableLogic = (profs, matieres, niveaux, classes , cours) => {
  const timetable = [];
  const processedNiveauNoms = new Set(); // Store processed niveau.nom values

  // Replace this with your actual timetable generation logic
  // Iterate over professors, matieres, niveaux, and classes to create timetable entries
  for (const prof of profs) {
    for (const matiere of matieres) {
      for (const niveau of niveaux) {
        // Check if this niveau.nom has been processed
          for (const classe of classes) {

            for( const cour of cours) {
              if( prof._id.toString() == cour.professeur.toString() && matiere._id.toString() == cour.matiere.toString())
              {
                  // Replace this with your actual logic for creating timetable entries
                  const entry = {
                    matiere: matiere._id,
                    heure: prof.preferences[0].heures[0], // Replace with your logic for generating time
                    date: new Date(), // Replace with your logic for generating date
                    prof: prof._id,
                    niveau: niveau._id,
                    classe: classe._id,
                    // Add more details as needed
                                }
                  timetable.push(entry);
              }
            };
            
          }

          // Mark this niveau.nom as processed
          processedNiveauNoms.add(niveau.nom);
      }
    }
  }

  return timetable;
};



export const getAllGenere = async (req, res) => {
  try {
    const getAllEdt = await GenerateEdt.find().populate("matiere").populate("prof").populate("niveau").populate("classe");
    res.status(200).json( getAllEdt );
  } catch (error) {
    res.status(500).send(error);
  }
};
