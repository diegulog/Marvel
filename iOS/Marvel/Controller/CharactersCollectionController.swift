//
//  CharactersCollectionController.swift
//  Marvel
//
//  Created by Diego on 16/11/2020.
//

import UIKit
import SDWebImage

class CharactersCollectionController: UICollectionViewController {
    
    @IBOutlet weak var flowLayout: UICollectionViewFlowLayout!
    
    var characters = [Character]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        configFlowLayout()
        Cliente.getCharacteres { (response, error) in
            if let error = error {
                // self.setError(error: error.localizedDescription)
                print(error.localizedDescription)
                return
            }
            self.characters = response
            self.collectionView.reloadData()
        }
    }
    
    func configFlowLayout() {
        let space:CGFloat = 2.0
        let dimension = (view.frame.size.width - (2 * space)) / 2.0
        flowLayout.minimumInteritemSpacing = space
        flowLayout.minimumLineSpacing = space
        flowLayout.itemSize = CGSize(width: dimension, height: dimension)
    }
    
    override func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let id = characters[indexPath.row].id
        performSegue(withIdentifier: "details", sender: id)
    }
    

    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return characters.count
    }
    
    
    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        let character = characters[indexPath.row]
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "CollectionViewCell", for: indexPath) as! CharacterCollectionViewCell
        cell.image.sd_setImage(with: character.thumbnailUrl(imageVariant: "standard_xlarge"), placeholderImage: UIImage(systemName: "photo"))
        cell.name.text = character.name
        return cell
    }
    
    // -------------------------------------------------------------------------
    // MARK: - Navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "details" {
            let vc = segue.destination as! DetailsViewController
            let id = sender as! Int
            vc.id = id
        }
    }
    
    
    override func numberOfSections(in collectionView: UICollectionView) -> Int {
        return  1
    }
    
}

