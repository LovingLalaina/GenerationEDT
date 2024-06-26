import Prof from "../model/professeur.js";
import Matiere from "../model/matiere.js";
import Niveau from "../model/niveau.js";
import Edt from "../model/edt.js";
import Classe from "../model/classe.js";
import GenerateEdt from "../model/generateEdt.js";


export const generateTimetable = async (req, res) => {
  try {
  
    const profs = await Prof.find();
    const matieres = await Matiere.find();
    const niveaux = await Niveau.find();
    const classes = await Classe.find();
    const cours = await Edt.find();

    
    const timetable = generateTimetableLogic(profs, matieres, niveaux, classes , cours);

    
    const saveToBase = await Promise.all(
      timetable.map((elem) => {
        const saveGenerate = new GenerateEdt({
          matiere: elem.matiere,
          heure: elem.heure, 
          date: elem.date, 
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


const generateTimetableLogic = (profs, matieres, niveaux, classes , cours) => {
  const timetable = [];
  const processedNiveauNoms = new Set(); 

  
  for (const prof of profs) {
    for (const matiere of matieres) {
      for (const niveau of niveaux) {
          for (const classe of classes) {
            for( const cour of cours) {
              if( prof._id.toString() == cour.professeur.toString() && matiere._id.toString() == cour.matiere.toString())
              {
                  
                  const entry = {
                    matiere: matiere._id,
                    heure: prof.preferences[0].heures[0], 
                    date: new Date(), 
                    prof: prof._id,
                    niveau: niveau._id,
                    classe: classe._id,
                    
                                }
                  timetable.push(entry);
              }
            };
            
          }

          
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
