//
//  ViewController.swift
//  Marvel
//
//  Created by Diego on 16/11/2020.
//

import UIKit
import SDWebImage

class CharactersCollectionController: UICollectionViewController {
    
    var characters = [Character]()
    @IBOutlet weak var flowLayout: UICollectionViewFlowLayout!
    
    override func viewDidLoad() {
        super.viewDidLoad()
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
    
    
    override func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        
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
    override func numberOfSections(in collectionView: UICollectionView) -> Int {
        return  1
    }
    
}

